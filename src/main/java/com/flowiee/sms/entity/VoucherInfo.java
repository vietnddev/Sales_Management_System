package com.flowiee.sms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.sms.core.BaseEntity;
import com.flowiee.sms.model.dto.VoucherInfoDTO;
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
    private BigDecimal discountPrice;

    @Column(name = "discount_price_max")
    private BigDecimal discountPriceMax;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
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
        voucherInfo.setDiscountPrice(dto.getDiscountPrice());
        voucherInfo.setDiscountPriceMax(dto.getDiscountPriceMax());
        voucherInfo.setStartTime(dto.getStartTime());
        voucherInfo.setEndTime(dto.getEndTime());
        return voucherInfo;
    }

	@Override
	public String toString() {
		return "VoucherInfo [id=" + super.id + ", title=" + title + ", description=" + description + ", doiTuongApDung=" + applicableObjects
				+ ", voucherType=" + voucherType + ", quantity=" + quantity + ", discount=" + discount + ", maxPriceDiscount=" + discountPriceMax + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
}