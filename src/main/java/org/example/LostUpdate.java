package org.example;

import java.sql.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class LostUpdate {

    private static final String URL = "jdbc:mysql://localhost:3306/highload_systems";
    private static final String USER = "root";
    private static final String PASSWORD = "vlad2003ua";

    public static void main(String[] args) throws SQLException, InterruptedException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            dropTable(connection);
            createTable(connection);
            createUser(connection);
        }

        long startTime = System.currentTimeMillis();
        lostUpdate();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
    }

    private static void dropTable(Connection connection) throws SQLException {
        String dropTable = "DROP TABLE IF EXISTS user_counter";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(dropTable);
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTable = """
                CREATE TABLE user_counter (
                  userId INT AUTO_INCREMENT PRIMARY KEY,
                  counter INT,
                  version INT
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTable);
        }
    }

    private static void createUser(Connection connection) throws SQLException {
        String createUser = "INSERT INTO user_counter (counter, version) VALUES (0, 1)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUser);
        }
    }

    private static void lostUpdate() throws InterruptedException {
        String selectUserCounter = "SELECT counter FROM user_counter WHERE userId = 1";
        String updateUserCounter = "UPDATE user_counter SET counter = ? WHERE userId = 1";

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            futures.add(executor.submit(() -> {
                try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    for (int j = 0; j < 10000; j++) {
                        try (PreparedStatement selectStmt = connection.prepareStatement(selectUserCounter);
                             ResultSet rs = selectStmt.executeQuery()) {
                            if (rs.next()) {
                                int counter = rs.getInt("counter");
                                counter++;
                                try (PreparedStatement updateStmt = connection.prepareStatement(updateUserCounter)) {
                                    updateStmt.setInt(1, counter);
                                    updateStmt.executeUpdate();
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));
        }


        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStmt = connection.prepareStatement(selectUserCounter);
             ResultSet rs = selectStmt.executeQuery()) {
            if (rs.next()) {
                int finalCounter = rs.getInt("counter");
                System.out.println("Counter: " + finalCounter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
