package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.dto.VoucherInfoDTO;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
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
    @Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "applicable_objects", nullable = false)
    private String applicableObjects;

    @Column(name = "type", length = 1000)
    private String voucherType;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "discount_price")
    private BigDecimal disountPrice;

    @Column(name = "discount_price_max")
    private BigDecimal maxPriceDiscount;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @JsonIgnore
    @OneToMany(mappedBy = "voucherInfo", fetch = FetchType.LAZY)
    private List<VoucherTicket> listVoucherTicket;

    public static VoucherInfo fromVoucherDTO(VoucherInfoDTO dto) {
        VoucherInfo voucherInfo = new VoucherInfo();
        voucherInfo.setId(dto.getId());
        voucherInfo.setTitle(dto.getTitle());
        voucherInfo.setDescription(dto.getDescription());
        voucherInfo.setApplicableObjects(dto.getApplicableObjects());
        voucherInfo.setVoucherType(dto.getVoucherType());
        voucherInfo.setQuantity(dto.getQuantity());
        voucherInfo.setDiscount(dto.getDiscount());
        voucherInfo.setDisountPrice(dto.getDiscountPrice());
        voucherInfo.setMaxPriceDiscount(dto.getDiscountPriceMax());
        voucherInfo.setStartTime(dto.getStartTime());
        voucherInfo.setEndTime(dto.getEndTime());
        return voucherInfo;
    }

	@Override
	public String toString() {
		return "VoucherInfo [id=" + super.id + ", title=" + title + ", description=" + description + ", doiTuongApDung=" + applicableObjects
				+ ", voucherType=" + voucherType + ", quantity=" + quantity + ", discount=" + discount + ", maxPriceDiscount=" + maxPriceDiscount + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
}