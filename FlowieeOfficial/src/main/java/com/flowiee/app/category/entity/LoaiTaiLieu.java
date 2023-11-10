package com.flowiee.app.category.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.entity.storage.DocField;
import com.flowiee.app.entity.storage.Document;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "dm_loai_tai_lieu")
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LoaiTaiLieu extends BaseEntity implements Serializable {
    @Column(name = "ma_loai", nullable = false)
    private String maLoai;

    @Column(name = "ten", nullable = false)
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "trang_thai", nullable = false)
    private boolean trangThai;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    @JsonIgnoreProperties("loaiTaiLieu")
    @OneToMany(mappedBy = "loaiTaiLieu", fetch = FetchType.LAZY)
    private List<Document> listDocument;

    @JsonIgnoreProperties("loaiTaiLieu")
    @OneToMany(mappedBy = "loaiTaiLieu", fetch = FetchType.LAZY)
    private List<DocField> listDocField;

    public LoaiTaiLieu (int id) {
        super.id = id;
    }

    @Override
    public String toString() {
        return "LoaiTaiLieu {id=" + id + ", maLoai=" + maLoai +", tenLoai=" + ten + ", ghiChu=" + moTa + ", isDefault=" + isDefault + ", trangThai=" + trangThai + "}";
    }
}