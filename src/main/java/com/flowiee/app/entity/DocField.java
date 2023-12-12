package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "stg_doc_field")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocField extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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

    @JsonIgnoreProperties("listDocField")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_tai_lieu_id", nullable = false)
    private Category loaiTaiLieu;

    @JsonIgnoreProperties("docField")
    @OneToMany(mappedBy = "docField", fetch = FetchType.LAZY)
    private List<DocData> listDocData;

    public DocField(Integer id) {
    	super.id = id;
    }

	@Override
	public String toString() {
		return "DocField [id=" + super.id + ", loaiField=" + loaiField + ", tenField=" + tenField + ", minLength=" + minLength
				+ ", maxLength=" + maxLength + ", minNumber=" + minNumber + ", maxNumber=" + maxNumber
				+ ", batBuocNhap=" + batBuocNhap + ", sapXep=" + sapXep + ", trangThai=" + trangThai + ", loaiTaiLieu="
				+ loaiTaiLieu + "]";
	}  
}