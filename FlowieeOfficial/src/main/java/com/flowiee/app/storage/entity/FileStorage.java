package com.flowiee.app.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.entity.product.Product;
import com.flowiee.app.entity.product.ProductVariant;
import com.flowiee.app.entity.system.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "file_storage")
public class FileStorage extends BaseEntity implements Serializable {
    @Column(name = "ten_file_customize")
    private String tenFileCustomize;

    @Column(name = "ten_file_khi_luu", nullable = false)
    private String tenFileKhiLuu;

    @Column(name = "ten_file_goc", nullable = false)
    private String tenFileGoc;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "kich_thuoc_file")
    private float kichThuocFile;

    @Column(name = "noi_dung")
    private byte[] noiDung;

    @Column(name = "directory_path", length = 500)
    private String directoryPath;

    @Column(name = "sort")
    private int sort;

    @Column(name = "trang_thai", nullable = false)
    private boolean status;

    @Column(name = "module", nullable = false)
    private String module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bien_the_san_pham_id")
    private ProductVariant productVariant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_by", nullable = false)
    private Account account;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    public FileStorage(MultipartFile file, String pModule) {
        try {
            this.module = pModule;
            this.tenFileGoc = file.getOriginalFilename();
            this.tenFileKhiLuu = Instant.now(Clock.systemUTC()).toEpochMilli() + "_" + file.getOriginalFilename();
            this.kichThuocFile = file.getSize();
            this.extension = FileUtil.getExtension(file.getOriginalFilename());
            this.contentType = file.getContentType();
            this.directoryPath = FileUtil.getPathDirectoty(pModule).substring(FileUtil.getPathDirectoty(pModule).indexOf("uploads"));
            this.account = FlowieeUtil.ACCOUNT;
            this.isActive = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}