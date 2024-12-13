package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.WriteConcern;
import org.bson.Document;

import java.util.concurrent.CountDownLatch;

public class MongoWriteConcern1Test {
    public static void incrementLikes(MongoCollection<Document> likesCollection, CountDownLatch latch) {
        int increments = 10000;
        for (int i = 0; i < increments; i++) {
            likesCollection.withWriteConcern(WriteConcern.ACKNOWLEDGED)
                    .findOneAndUpdate(
                            new Document("_id", "likesCounterConcern1"),
                            new Document("$inc", new Document("counter", 1)),
                            new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
                    );
        }
        latch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        MongoClient client = MongoClients.create("mongodb://localhost:27018");
        MongoDatabase database = client.getDatabase("test");
        MongoCollection<Document> likesCollection = database.getCollection("likes");

        if (likesCollection.countDocuments(new Document("_id", "likesCounterConcern1")) == 0) {
            likesCollection.insertOne(new Document("_id", "likesCounterConcern1").append("counter", 0));
        }

        long startTime = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(10);

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

        Document finalResult = likesCollection.find(new Document("_id", "likesCounterConcern1")).first();
        if (finalResult != null) {
            System.out.println("Final like count: " + finalResult.getInteger("counter"));
        } else {
            System.out.println("Document not found.");
        }

        client.close();
    }
}
