package com.example.lab3.repositories;

import com.example.lab3.models.Item;
import com.example.lab3.models.Order;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface OrderRepository extends Neo4jRepository<Order, Long> {
    @Query("MATCH (o:Order)-[:CONTAINS]->(i:Item) WHERE o.orderId = $__orderId__ RETURN i")
    List<Item> findItemsInOrder(Long orderId);

    @Query("MATCH (o:Order)-[:CONTAINS]->(i:Item) WHERE o.orderId = $__orderId__ RETURN sum(i.price)")
    Double calculateOrderTotal(Long orderId);
}
