package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import com.flowiee.app.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Builder
@Entity
@Table(name = "stg_file_storage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileStorage extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "customize_name")
    private String tenFileCustomize;

    @Column(name = "saved_name", nullable = false)
    private String tenFileKhiLuu;

    @Column(name = "original_name", nullable = false)
    private String tenFileGoc;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "file_size")
    private float kichThuocFile;

    @Column(name = "content")
    private byte[] noiDung;

    @Column(name = "directory_path", length = 500)
    private String directoryPath;

    @Column(name = "sort")
    private int sort;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "module", nullable = false)
    private String module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_by", nullable = false)
    private Account account;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "fileStorage", fetch = FetchType.LAZY)
    private List<DocHistory> listDocHistory;

    public FileStorage(MultipartFile file, String pModule) {
        try {
            this.module = pModule;
            this.tenFileGoc = file.getOriginalFilename();
            this.tenFileKhiLuu = Instant.now(Clock.systemUTC()).toEpochMilli() + "_" + file.getOriginalFilename();
            this.kichThuocFile = file.getSize();
            this.extension = CommonUtils.getExtension(file.getOriginalFilename());
            this.contentType = file.getContentType();
            this.directoryPath = CommonUtils.getPathDirectory(pModule).substring(CommonUtils.getPathDirectory(pModule).indexOf("uploads"));
            this.isActive = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public String toString() {
		return "FileStorage [id=" + super.id + ", tenFileCustomize=" + tenFileCustomize + ", tenFileKhiLuu=" + tenFileKhiLuu
				+ ", tenFileGoc=" + tenFileGoc + ", ghiChu=" + ghiChu + ", extension=" + extension + ", contentType="
				+ contentType + ", kichThuocFile=" + kichThuocFile + ", noiDung=" + Arrays.toString(noiDung)
				+ ", directoryPath=" + directoryPath + ", sort=" + sort + ", status=" + status + ", module=" + module
				+ ", product=" + product + ", productVariant=" + productVariant + ", document=" + document
				+ ", account=" + account + ", isActive=" + isActive + "]";
	}        
}