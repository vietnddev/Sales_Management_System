package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID", unique = true, nullable = false)
    private int productID;
    private String code;
    private String type;
    private String name;
    private String color;
    private Double price;
    private String size;
    private String date;
    private int storage;
    private int quantity;
    private String describes;
    private boolean status;
    private int promotion;

    public Product(String code, String type, String name, String color, Double price, String size, String date,
                   int storage, int quantity, String describes, boolean status, int promotion) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.color = color;
        this.price = price;
        this.size = size;
        this.date = date;
        this.storage = storage;
        this.quantity = quantity;
        this.describes = describes;
        this.status = status;
        this.promotion = promotion;
    }
}