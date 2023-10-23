package com.flowiee.app.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.hethong.entity.Account;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "stg_document")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "parent_id", nullable = false)
    private int parentId;

    @Column(name = "loai", nullable = false)
    private String loai;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "alias_name", nullable = false)
    private String aliasName;

    @Column(name = "mo_ta")
    private String moTa;

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

    @JsonIgnoreProperties("listDocument")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, insertable = true, updatable = true)
    private Account account;

    @JsonIgnoreProperties("listDocument")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_tai_lieu_id", nullable = true, insertable = true, updatable = true)
    private LoaiTaiLieu loaiTaiLieu;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocData> listDocData;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileStorage> listDocFile;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocShare> listDocShare;

    @Override
    public String toString() {
        return "Document{" +
            "id=" + id +
            ", parentId=" + parentId +
            ", loai='" + loai + '\'' +
            ", ten='" + ten + '\'' +
            ", aliasName='" + aliasName + '\'' +
            ", moTa='" + moTa + '\'' +
            ", taiKhoan=" + account +
            ", loaiTaiLieu=" + loaiTaiLieu +
            '}';
    }

//    @Transient
//    private List<Document> subThuMuc = new ArrayList<>();;
//
//    public void addSubStorage(Document subStorage) {
//        subThuMuc.add(subStorage);
//    }
}