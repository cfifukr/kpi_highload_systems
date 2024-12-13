package org.example;

import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

import java.util.concurrent.CountDownLatch;

public class MongoWriteConcernMajorityTest {

    public static void incrementLikes(MongoCollection<Document> likesCollection, CountDownLatch latch) {
        int increments = 10000;
        for (int i = 0; i < increments; i++) {
            likesCollection.findOneAndUpdate(
                    new Document("_id", "likesCounterW1"),
                    new Document("$inc", new Document("counter", 1)),
                    new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
            );
        }
        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("test");

        final MongoCollection<Document> likesCollection = database.getCollection("likes");

        likesCollection.withWriteConcern(WriteConcern.MAJORITY);

        long startTime = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(10);  // 10 threads

        for (int i = 0; i < 10; i++) {
            final int clientId = i;
            new Thread(() -> {
                incrementLikes(likesCollection, latch);
                System.out.println("Client " + clientId + " finished.");
            }).start();
        }

        latch.await();

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Time : " + elapsedTime + " milliseconds.");

        Document finalCounter = likesCollection.find(new Document("_id", "likesCounterConcernMajority")).first();
        System.out.println("Final counter value: " + finalCounter.get("counter"));

        client.close();
    }
}
