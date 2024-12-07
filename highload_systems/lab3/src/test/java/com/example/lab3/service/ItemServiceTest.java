package com.example.lab3.service;

import com.example.lab3.models.Item;
import com.example.lab3.repositories.ItemRepository;
import com.example.lab3.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "spring.profiles.active=test")
public class ItemServiceTest {


    @Autowired
    private ItemService itemService;


    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testIncrementLikes() throws InterruptedException {
        Item item = new Item();
        item.setName("Test");
        item.setPrice(100.0);
        item.setLikes(0);
        item = itemRepository.save(item);

        final Long itemId = item.getItemId();

        int numThreads = 10;
        int likesPerThread = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < likesPerThread; j++) {
                        Integer res = itemService.incrementLikes(itemId);
                        System.out.println("Incremented to: " + res);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        if (!latch.await(1000, TimeUnit.SECONDS)) {
            System.out.println("Times gone");
        }

        executor.shutdown();

        long endTime = System.currentTimeMillis();

        Item updatedItem = itemRepository.findById(itemId).orElseThrow();
        System.out.println("К-ть likes: " + updatedItem.getLikes());
        System.out.println("Час без синхронизації: " + (endTime - startTime) + " ms");

        assertEquals(numThreads * likesPerThread, updatedItem.getLikes());
    }


    @Test
    public void testIncrementLikesSynchronized() throws InterruptedException {
        Item item = new Item();
        item.setName("Test Synchronized");
        item.setPrice(100.0);
        item.setLikes(0);
        item = itemRepository.save(item);

        final Long itemId = item.getItemId();

        int numThreads = 10;
        int likesPerThread = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < likesPerThread; j++) {
                        Integer res = itemService.incrementLikesSynchronized(itemId);
                        System.out.println("Incremented to: " + res);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        if (!latch.await(1000, TimeUnit.SECONDS)) {
            System.out.println("Times gone");
        }

        executor.shutdown();

        long endTime = System.currentTimeMillis();

        Item updatedItem = itemRepository.findById(itemId).orElseThrow();
        System.out.println("К-ть likes: " + updatedItem.getLikes());
        System.out.println("Час з синхронизацією: " + (endTime - startTime) + " ms");

        assertEquals(numThreads * likesPerThread, updatedItem.getLikes());
    }
}

