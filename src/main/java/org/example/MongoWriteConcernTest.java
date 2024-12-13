package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import java.util.concurrent.CountDownLatch;

public class MongoWriteConcernTest {
    public static void incrementLikes(MongoCollection<Document> likesCollection, CountDownLatch latch) {
        int increments = 10000;
        for (int i = 0; i < increments; i++) {
            likesCollection.findOneAndUpdate(
                    new Document("_id", "likesCounter"),
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

        long startTime = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(10); // 10 потоків

        for (int i = 0; i < 10; i++) {
            final int clientId = i;
            new Thread(() -> {
                incrementLikes(likesCollection, latch);
                System.out.println("Client " + clientId + " finished.");
            }).start();
        }

        latch.await();

        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Time: " + elapsedTime );
        client.close();
    }
}
