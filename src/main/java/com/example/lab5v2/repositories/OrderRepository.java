package com.example.lab5v2.repositories;

import com.example.lab5v2.models.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends CassandraRepository<Order, UUID> {

    @Query("SELECT * FROM my_keyspace.orders WHERE customer_name = ?0 ORDER BY order_date DESC")
    List<Order> findByCustomerNameOrderByOrderDateDesc(String customerName);

    @Query("SELECT SUM(total_price) FROM my_keyspace.orders WHERE customer_name = ?0")
    BigDecimal findTotalAmountByCustomerName(String customerName);

    @Query("SELECT WRITETIME(total_price) FROM my_keyspace.orders WHERE id = ?0")
    Long findWriteTimeByOrderId(UUID orderId);
}
