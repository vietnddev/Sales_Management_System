package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Gallery")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private int galleryId;
    private int productVariantID;
    private String type;
    private String url;
    private int sort;
    private String note;
    private boolean status;
    private boolean isMain;
    private String extension;
    private String fileName;
    
	public Gallery(int productVariantID, String type, String url, int sort, String note, boolean status, boolean isMain, String extension, String fileName) {
		this.productVariantID = productVariantID;
		this.type = type;
		this.url = url;
		this.sort = sort;
		this.note = note;
		this.status = status;
		this.isMain = isMain;
		this.extension = extension;
		this.fileName = fileName;
	}      
}
