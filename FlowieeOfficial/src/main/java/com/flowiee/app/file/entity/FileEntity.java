package com.flowiee.app.file.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.products.entity.BienTheSanPham;
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
@Table(name = "file_storage")
public class FileEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "tieu_de", length = 255, nullable = true)
    private String tieuDe;

    @Column(name = "extension", length = 10, nullable = false)
    private String extension;

    @Column(name = "contentType", length = 50, nullable = false)
    private String contentType;

    @Column(name = "noi_dung", nullable = true)
    private byte[] noiDung;

    @Column(name = "ten_file_goc", length = 255, nullable = false)
    private String tenGoc;

    @Column(name = "ten_file_khi_luu", length = 255, nullable = false)
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

    @Column(name = "trang_thai", nullable = false)
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
    private BienTheSanPham productVariant;
}