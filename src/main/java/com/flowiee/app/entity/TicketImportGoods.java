package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class TicketImportGoods extends BaseEntity implements Serializable {
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

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "received_time")
    private Date receivedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by")
    private Account receivedBy;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "ticketImportGoods", fetch = FetchType.LAZY)
    private List<Material> listMaterial;

    @OneToMany(mappedBy = "ticketImportGoods", fetch = FetchType.LAZY)
    private List<ProductVariant> listProductVariant;

    @OneToMany(mappedBy = "ticketImportGoods", fetch = FetchType.LAZY)
    private List<MaterialTemp> listMaterialTemp;

    @OneToMany(mappedBy = "ticketImportGoods", fetch = FetchType.LAZY)
    private List<ProductVariantTemp> listProductVariantTemp;

    public TicketImportGoods(Integer id) {
        super.id = id;
    }

    public TicketImportGoods(Integer id, String title) {
        super.id = id;
        this.title = title;
    }

	@Override
	public String toString() {
		return "TicketImportGoods [id=" + super.id + ", title=" + title + ", supplier=" + supplier + ", discount=" + discount
				+ ", paymentMethod=" + paymentMethod + ", paidAmount=" + paidAmount + ", paidStatus=" + paidStatus
				+ ", orderTime=" + orderTime + ", receivedTime=" + receivedTime + ", receivedBy=" + receivedBy
				+ ", note=" + note + ", status=" + status + "]";
	}
}