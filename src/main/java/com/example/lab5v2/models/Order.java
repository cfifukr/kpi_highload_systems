package com.example.lab5v2.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;
import java.util.UUID;

@Table("orders")
public class Order {
    @PrimaryKey
    private UUID id;
    private UUID itemId;
    private String customerName;
    private String orderDate;
    private int quantity;


    public Order(){}

    public Order(UUID id, UUID itemId, String customerName, String orderDate, int quantity) {
        this.id = id;
        this.itemId = itemId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getQuantity() == order.getQuantity() && Objects.equals(getId(), order.getId()) && Objects.equals(getItemId(), order.getItemId()) && Objects.equals(getCustomerName(), order.getCustomerName()) && Objects.equals(getOrderDate(), order.getOrderDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getItemId(), getCustomerName(), getOrderDate(), getQuantity());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", customerName='" + customerName + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
