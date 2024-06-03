package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.MaterialTemp;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.model.dto.TicketImportDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ticket_import_goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketImport extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

	@Column(name = "title", nullable = false)
    private String title;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "discount")
    private Float discount;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method")
    private Category paymentMethod;

    @Column(name = "paid_amount")
    private Float paidAmount;

    @Column(name = "pay_status")
    private String paidStatus;

    @Column(name = "importer")
    private String importer;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
//    @JsonSerialize(using = DateSerializer.class)
//    @JsonDeserialize(using = DateDeserializers.DateDeserializer.class)
    @Column(name = "import_time")
    private LocalDateTime importTime;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<MaterialTemp> listMaterialTemps;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<ProductVariantTemp> listProductVariantTemps;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<FileStorage> listImages;

    @Transient
    private BigDecimal totalValue;

    public TicketImport(Integer id) {
        super.id = id;
    }

    public TicketImport(Integer id, String title) {
        super.id = id;
        this.title = title;
    }

    public static TicketImport fromTicketImportDTO(TicketImportDTO dto) {
        TicketImport ticketImport = new TicketImport();
        ticketImport.setId(dto.getId());
        ticketImport.setTitle(dto.getTitle());
        ticketImport.setImporter(dto.getImporter());
        ticketImport.setImportTime(dto.getImportTime());
        ticketImport.setNote(dto.getNote());
        ticketImport.setStatus(dto.getStatus());
        ticketImport.setListProductVariantTemps(dto.getListProductVariantTemp());
        ticketImport.setListMaterialTemps(dto.getListMaterialTemp());
        return ticketImport;
    }

	@Override
	public String toString() {
		return "TicketImportGoods [id=" + super.id + ", title=" + title + ", supplier=" + supplier + ", discount=" + discount
				+ ", paymentMethod=" + paymentMethod + ", paidAmount=" + paidAmount + ", paidStatus=" + paidStatus
				+ ", note=" + note + ", status=" + status + "]";
	}
}