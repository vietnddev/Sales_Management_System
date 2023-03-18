package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.model.storage.Filess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "Product_Variants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product_Variants implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productVariantID", unique = true, nullable = false)    
    private int productVariantID;
    
    @Column(name = "productID")
    private int productID;
    
    @Column(name = "code")
    private String code; 
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "value")
    private String value;
    
    @Column(name = "status")
    private boolean status;
           
    @OneToMany(mappedBy = "productVariant")
    private List<Filess> filess;
}  
