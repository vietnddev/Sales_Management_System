package com.flowiee.app.khotailieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "stg_doc_field")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocField implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "loai_field", nullable = false)
    private String loaiField;

    @Column(name = "ten_field", nullable = false)
    private String tenField;

    @Column(name = "min_length", nullable = false)
    private int minLength;

    @Column(name = "max_length", nullable = false)
    private int maxLength;

    @Column(name = "min_number", nullable = false)
    private int minNumber;

    @Column(name = "max_number", nullable = false)
    private int maxNumber;

    @Column(name = "bat_buoc_nhap", nullable = false)
    private boolean batBuocNhap;

    @Column(name = "sap_xep")
    private int sapXep;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @CreatedDate
    @Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  updatable = false)
    private Date createdAt;

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        if (createdAt == null) {
            createdAt = new Date();
        }
    }

    @JsonIgnoreProperties("listDocField")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_tai_lieu_id", nullable = false)
    private LoaiTaiLieu loaiTaiLieu;

    @JsonIgnoreProperties("docField")
    @OneToMany(mappedBy = "docField", fetch = FetchType.LAZY)
    private List<DocData> listDocData;

    @Override
    public String toString() {
        return "DocField{" +
            "id=" + id +
            ", loaiField='" + loaiField + '\'' +
            ", tenField='" + tenField + '\'' +
            ", batBuocNhap=" + batBuocNhap +
            ", sapXep=" + sapXep +
            ", trangThai=" + trangThai +
            ", loaiTaiLieu=" + loaiTaiLieu +
            '}';
    }
}