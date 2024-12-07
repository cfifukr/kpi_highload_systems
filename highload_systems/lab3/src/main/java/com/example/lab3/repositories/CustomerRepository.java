package com.example.lab3.repositories;


import com.example.lab3.models.Customer;
import com.example.lab3.models.Item;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

    @Query("MATCH (c:Customer)-[:PLACED]->(o:Order)-[:CONTAINS]->(i:Item) WHERE c.customerId = $__customerId__ RETURN i")
    List<Item> findItemsBoughtByCustomer(Long customerId);

    @Query("MATCH (c:Customer)-[:VIEWED]->(i:Item) WHERE c.customerId = $__customerId__ RETURN i")
    List<Item> findItemsViewedByCustomer(Long customerId);

    @Query("MATCH (c:Customer)-[:VIEWED]->(i:Item) WHERE c.id = $__customerId__ AND NOT (c)-[:PLACED]->(:Order)-[:CONTAINS]->(i) RETURN i")
    List<Item> findItemsViewedNotBought(Long customerId);
}
