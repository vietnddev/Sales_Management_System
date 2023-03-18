package com.flowiee.app.model.storage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product_Files implements Serializable{
    @Column(name = "fileID") 
    private int fileID;
        
    @Column(name = "productVariantID")
    private int productVariantID;
     
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
    
    @Column (name = "productID")
    private int productID;
}
