package org.example;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.IAtomicLong;

public class IAtomicLongCounter {

    private static final String COUNTER_NAME = "myCounter";

    public static void main(String[] args) {
        Config config = new Config();
        config.getCPSubsystemConfig().setCPMemberCount(3);
        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);

        TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
        tcpIpConfig.setEnabled(true)
                .addMember("127.0.0.1:5701")
                .addMember("127.0.0.1:5702")
                .addMember("127.0.0.1:5703");


        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);

        try {
            // Отримання IAtomicLong
            IAtomicLong atomicLong = hazelcastInstance.getCPSubsystem().getAtomicLong(COUNTER_NAME);

            // Інкрементування значення
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 100000; i++) {
                atomicLong.incrementAndGet();
            }
            long endTime = System.currentTimeMillis();

            // Отримання кінцевого значення
            long finalValue = atomicLong.get();

            System.out.println("Кінцеве значення каунтера: " + finalValue);
            System.out.println("Час виконання: " + (endTime - startTime) + " мс");

        } finally {
            // Закриття Hazelcast instance
            hazelcastInstance.shutdown();
        }
    }
}

