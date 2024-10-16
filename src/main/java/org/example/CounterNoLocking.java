package org.example;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterNoLocking {
    public static void main(String[] args) {
        Config config = new Config();
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);

        TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
        tcpIpConfig.setEnabled(true)
                .addMember("127.0.0.1:5701")
                .addMember("127.0.0.1:5702")
                .addMember("127.0.0.1:5703");

        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        IMap<String, Integer> distributedMap = hazelcastInstance.getMap("myDistributedMap");

        String key = "counter";
        distributedMap.put(key, 0);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long startTime = System.currentTimeMillis();

        for (int t = 0; t < 10; t++) {
            executorService.submit(() -> {
                for (int i = 0; i < 10_000; i++) {
                    Integer currentValue = distributedMap.get(key);
                    distributedMap.put(key, currentValue + 1);
                }
            });
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
        long endTime = System.currentTimeMillis(); // Кінець вимірювання часу


        System.out.println("Final value of counter: " + distributedMap.get(key));
        System.out.println("Time: " + (endTime - startTime) + " ms");

        hazelcastInstance.shutdown();
    }

}


