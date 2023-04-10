package com.flowiee.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "file")
public class FileEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileID", unique = true, nullable = false)
    private int fileID;

    private String title;

    private String extension;

    private String contentType;

    private byte[] content;

    private String originalName;

    private String storageName;

    private String directoryPath;

    private String createdBy;
    private String updatedBy;
    private String deletedBy;

    @CreatedDate
    @Column(name = "CreatedAt",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "UpdatedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
    private Date updatedAt;
    @UpdateTimestamp
    @Column(name = "DeletedAt", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = true)
    private Date deleteAt;

    private int sort;

    private boolean isActive;

    private boolean status;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        if (createdAt == null) {
            createdAt = new Date();
        }
    }
          
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "productVariantID")
    private Product_Variants productVariant; 
}