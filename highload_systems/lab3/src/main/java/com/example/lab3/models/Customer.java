package com.example.lab3.models;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {


    @Id
    @GeneratedValue
    private Long customerId;

    private String name;

    @Relationship(type = "PLACED", direction = Relationship.Direction.OUTGOING)
    private List<Order> orders = new ArrayList<>();


}
