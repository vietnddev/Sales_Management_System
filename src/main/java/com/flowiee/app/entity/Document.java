package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Integer parentId;

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

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    private List<DocHistory> listDocHistory;

    public Document(Integer id) {
    	super.id = id;
    }
    
    public Document(Integer id, String name) {
    	this.ten = name;
    }
    
    public Map<String, String> compareTo(Document documentToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.getParentId().equals(documentToCompare.getParentId())) {
            map.put("Move", "From " +this.getParentId() + "#To " + documentToCompare.getParentId());
        }
        if (!this.getTen().equals(documentToCompare.getTen())) {
            map.put("Document name", this.getTen() + "#" + documentToCompare.getTen());
        }
        if (!this.getLoaiTaiLieu().getName().equals(documentToCompare.getLoaiTaiLieu().getName())) {
            map.put("Document type", this.getLoaiTaiLieu().getName() + "#" + documentToCompare.getLoaiTaiLieu().getName());
        }
        if (!this.getMoTa().equals(documentToCompare.getMoTa())) {
            String descriptionOld = this.getMoTa().length() > 9999 ? this.getMoTa().substring(0, 9999) : this.getMoTa();
            String descriptionNew = documentToCompare.getMoTa().length() > 9999 ? documentToCompare.getMoTa().substring(0, 9999) : documentToCompare.getMoTa();
            map.put("Description", descriptionOld + "#" + descriptionNew);
        }
        return map;
    }

	@Override
	public String toString() {
		return "Document [id=" + super.id + ", parentId=" + parentId + ", loai=" + loai + ", ten=" + ten + ", aliasName=" + aliasName
				+ ", moTa=" + moTa + ", loaiTaiLieu=" + loaiTaiLieu + "]";
	}        
}