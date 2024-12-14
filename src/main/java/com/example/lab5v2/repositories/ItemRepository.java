package com.example.lab5v2.repositories;

import com.example.lab5v2.models.Item;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ItemRepository extends CassandraRepository<Item, UUID> {

    @Query("SELECT * FROM my_keyspace.items WHERE category = ?0 ORDER BY price ASC")
    List<Item> findByCategoryOrderByPrice(String category);


    @Query("SELECT * FROM my_keyspace.by_name WHERE category = ?0 AND name = ?1")
    List<Item> findByNameAndCategory(String category, String name);

    @Query("SELECT * FROM my_keyspace.by_price_range WHERE category = ?0 AND price >= ?1 AND price <= ?2")
    List<Item> findByPriceRangeAndCategory(String category, BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT * FROM my_keyspace.by_price_and_manufacturer WHERE category = ?0 AND price = ?1 AND manufacturer = ?2")
    List<Item> findByPriceAndManufacturerAndCategory(String category, BigDecimal price, String manufacturer);


}
