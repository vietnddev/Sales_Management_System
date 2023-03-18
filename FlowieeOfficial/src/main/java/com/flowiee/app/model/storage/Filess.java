package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.model.sales.Product_Variants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Filess")
public class Filess implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileID", unique = true, nullable = false) 
    private int fileID;        
     
    @Column(name = "type")
    private String type;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "sort")
    private int sort;
    
    @Column(name = "note")
    private String note;
    
    @Column(name = "status")
    private boolean status;
    
    @Column(name = "isMain")
    private boolean isMain;
    
    @Column(name = "extension")
    private String extension;
    
    @Column(name = "fileName")
    private String fileName;
          
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "productVariantID")
    private Product_Variants productVariant; 
}