package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stg_ticket_import_goods")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TicketImport extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "discount")
    private Float discount;

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
    private Date importTime;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<Material> listMaterial;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<ProductVariant> listProductVariant;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<MaterialTemp> listMaterialTemp;

    @JsonIgnore
    @OneToMany(mappedBy = "ticketImport", fetch = FetchType.LAZY)
    private List<ProductVariantTemp> listProductVariantTemp;

    public TicketImport(Integer id) {
        super.id = id;
    }

    public TicketImport(Integer id, String title) {
        super.id = id;
        this.title = title;
    }

	@Override
	public String toString() {
		return "TicketImportGoods [id=" + super.id + ", title=" + title + ", supplier=" + supplier + ", discount=" + discount
				+ ", paymentMethod=" + paymentMethod + ", paidAmount=" + paidAmount + ", paidStatus=" + paidStatus
				+ ", note=" + note + ", status=" + status + "]";
	}
}