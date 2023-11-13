package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.category.Category;

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
public class Document extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
    @JoinColumn(name = "loai_tai_lieu_id")
    private Category loaiTaiLieu;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocData> listDocData;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileStorage> listDocFile;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocShare> listDocShare;

    public Document(Integer id) {
    	super.id = id;
    }
    
    public Document(Integer id, String name) {
    	this.ten = name;
    }
    
    @Override
    public String toString() {
        return "Document{" +
            "id=" + id +
            ", parentId=" + parentId +
            ", loai='" + loai + '\'' +
            ", ten='" + ten + '\'' +
            ", aliasName='" + aliasName + '\'' +
            ", moTa='" + moTa + '\'' +
            ", loaiTaiLieu=" + loaiTaiLieu +
            '}';
    }
}