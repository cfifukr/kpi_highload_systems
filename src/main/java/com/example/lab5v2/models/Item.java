package com.example.lab5v2.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Table("items")
public class Item {
    @PrimaryKey
    private UUID id;
    private String name;
    private String category;
    private BigDecimal price;
    private String manufacturer;
    private Map<String, String> additionalProperties;

    public Item() {}

    public Item(UUID id, String name, String category, BigDecimal price, String manufacturer, Map<String, String> additionalProperties) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.manufacturer = manufacturer;
        this.additionalProperties = additionalProperties;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, String> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return Objects.equals(getId(), item.getId()) && Objects.equals(getName(), item.getName()) && Objects.equals(getCategory(), item.getCategory()) && Objects.equals(getPrice(), item.getPrice()) && Objects.equals(getManufacturer(), item.getManufacturer()) && Objects.equals(getAdditionalProperties(), item.getAdditionalProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCategory(), getPrice(), getManufacturer(), getAdditionalProperties());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}

