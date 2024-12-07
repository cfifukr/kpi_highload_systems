package com.example.lab3.repositories;


import com.example.lab3.models.Customer;
import com.example.lab3.models.Item;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Map;
public interface ItemRepository extends Neo4jRepository<Item, Long> {
    @Query("MATCH (i:Item)<-[:CONTAINS]-(:Order)-[:PLACED_BY]->(c:Customer) WHERE i.itemId = $__itemId__ RETURN c")
    List<Customer> findCustomersWhoBoughtItem(Long itemId);

    @Query("MATCH (i:Item)<-[:CONTAINS]-(:Order)-[:CONTAINS]->(other:Item) WHERE i.itemId = $__itemId__ AND i <> other RETURN other")
    List<Item> findItemsBoughtWith(Long itemId);

    @Query("MATCH (i:Item)<-[:CONTAINS]-(:Order) RETURN i, count(*) as purchaseCount ORDER BY purchaseCount DESC")
    List<Map<String, Object>> countItemPurchases();

    @Query("MATCH (i:Item) WHERE i.itemId = $__itemId__ SET i.likes = i.likes + 1 RETURN i.likes")
    Integer incrementLikes(Long itemId);

}

