package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Price_History")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Price_History implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int priceHistory;
    private int productVariantID;
    private float price;
    private String createdAt;
    private String UpdatedAt;
}
