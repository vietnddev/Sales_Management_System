package com.flowiee.app.khotailieu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.hethong.entity.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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

    @JsonIgnoreProperties("listDocument")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, insertable = true, updatable = true)
    private Account account;

    @JsonIgnoreProperties("listDocument")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_tai_lieu_id", nullable = true, insertable = true, updatable = true)
    private LoaiTaiLieu loaiTaiLieu;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<DocData> listDocData;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<FileStorage> listDocFile;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
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
}