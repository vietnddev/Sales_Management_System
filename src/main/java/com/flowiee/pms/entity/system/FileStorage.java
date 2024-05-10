package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

@Builder
@Entity
@Table(name = "file_storage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileStorage extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "customize_name")
    private String customizeName;

    @Column(name = "saved_name", nullable = false)
    private String storageName;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "note")
    private String note;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "content")
    private byte[] content;

    @Column(name = "directory_path", length = 500)
    private String directoryPath;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "module", nullable = false)
    private String module;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductDetail productDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_by", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_import_id")
    private TicketImport ticketImport;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Transient
    private String src;

    public FileStorage(MultipartFile file, String pModule, Integer productId) {
        try {
            this.module = pModule;
            this.originalName = file.getOriginalFilename();
            this.customizeName = file.getOriginalFilename();
            //this.storageName = Instant.now(Clock.systemUTC()).toEpochMilli() + "_" + file.getOriginalFilename();
            this.fileSize = file.getSize();
            this.extension = CommonUtils.getExtension(file.getOriginalFilename());
            this.contentType = file.getContentType();
            this.directoryPath = CommonUtils.getPathDirectory(pModule).substring(CommonUtils.getPathDirectory(pModule).indexOf("uploads"));
            this.account = new Account(CommonUtils.getUserPrincipal().getId());
            this.product = new Product(productId);
            this.isActive = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public String toString() {
		return "FileStorage [id=" + super.id + ", customizeName=" + customizeName + ", storageName=" + storageName
				+ ", originalName=" + originalName + ", ghiChu=" + note + ", extension=" + extension + ", contentType="
				+ contentType + ", fileSize=" + fileSize + ", content=" + Arrays.toString(content)
				+ ", directoryPath=" + directoryPath + ", sort=" + sort + ", status=" + status + ", module=" + module
				+ ", product=" + product + ", productVariant=" + productDetail + ", account=" + account + ", isActive=" + isActive + "]";
	}        
}