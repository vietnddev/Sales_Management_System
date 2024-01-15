package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "pro_voucher_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VoucherInfo extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "applicable_to", nullable = false)
    private String doiTuongApDung;

    @Column(name = "type", length = 1000)
    private String voucherType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "length_of_key", nullable = false)
    private Integer lengthOfKey;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "max_price_discount")
    private Float maxPriceDiscount;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @OneToMany(mappedBy = "voucherInfo", fetch = FetchType.LAZY)
    private List<VoucherTicket> listVoucherTicket;

	@Override
	public String toString() {
		return "VoucherInfo [id=" + super.id + ", title=" + title + ", description=" + description + ", doiTuongApDung=" + doiTuongApDung
				+ ", voucherType=" + voucherType + ", quantity=" + quantity + ", lengthOfKey=" + lengthOfKey
				+ ", discount=" + discount + ", maxPriceDiscount=" + maxPriceDiscount + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
}