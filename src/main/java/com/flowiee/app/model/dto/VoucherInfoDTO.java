package com.flowiee.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flowiee.app.entity.VoucherInfo;
import com.flowiee.app.entity.VoucherTicket;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Column;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class VoucherInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "applicable_objects")
    private String applicableObjects;

    @Column(name = "type")
    private String voucherType;

    @Column(name = "quantity")
    private Integer quantity;

    private Integer length;

    @Column(name = "discount")
    private Integer discount;

    private BigDecimal discountPrice;

    @Column(name = "discount_price_max")
    private BigDecimal discountPriceMax;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    @Column(name = "start_time")
    private Date startTime;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "GMT+7")
    @Column(name = "end_time")
    private Date endTime;

    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "created_by")
    private Integer createdBy;

    private List<VoucherTicket> listVoucherTicket;
    private List<ProductDTO> applicableProducts;

    public static VoucherInfoDTO fromVoucherInfo(VoucherInfo voucherInfo) {
        try {
            VoucherInfoDTO voucherInfoDTO = new VoucherInfoDTO();
            voucherInfoDTO.setId(voucherInfo.getId());
            voucherInfoDTO.setTitle(voucherInfo.getTitle());
            voucherInfoDTO.setDescription(voucherInfo.getDescription());
            voucherInfoDTO.setApplicableObjects(voucherInfo.getApplicableObjects());
            voucherInfoDTO.setVoucherType(voucherInfo.getVoucherType());
            voucherInfoDTO.setQuantity(voucherInfo.getQuantity());
            voucherInfoDTO.setDiscount(voucherInfo.getDiscount());
            voucherInfoDTO.setDiscountPriceMax(voucherInfo.getMaxPriceDiscount());
            voucherInfoDTO.setStartTime(voucherInfo.getStartTime());
            voucherInfoDTO.setEndTime(voucherInfo.getEndTime());
            //Checking status
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = DateUtils.convertStringToDate(String.valueOf(voucherInfo.getStartTime()).substring(0, 10), "yyyy-MM-dd");
            Date endDate = DateUtils.convertStringToDate(String.valueOf(voucherInfo.getEndTime()).substring(0, 10), "yyyy-MM-dd");
            Date currentDate = DateUtils.convertStringToDate(formatter.format(new Date()), "yyyy-MM-dd");
            if ((startDate.before(currentDate) || startDate.equals(currentDate)) && (endDate.after(currentDate) || endDate.equals(currentDate))) {
                voucherInfoDTO.setStatus(AppConstants.VOUCHER_STATUS.A.getLabel());
            } else {
                voucherInfoDTO.setStatus(AppConstants.VOUCHER_STATUS.I.getLabel());
            }
            voucherInfoDTO.setListVoucherTicket(voucherInfo.getListVoucherTicket());

            return voucherInfoDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<VoucherInfoDTO> fromVoucherInfos(List<VoucherInfo> voucherInfos) {
        List<VoucherInfoDTO> list = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(voucherInfos)) {
            for (VoucherInfo v : voucherInfos) {
                list.add(VoucherInfoDTO.fromVoucherInfo(v));
            }
        }
        return list;
    }

	@Override
	public String toString() {
		return "VoucherInfoDTO [id=" + id + ", title=" + title + ", description=" + description + ", doiTuongApDung="
				+ applicableObjects + ", voucherType=" + voucherType + ", quantity=" + quantity
				+ ", discount=" + discount + ", maxPriceDiscount=" + discountPriceMax + ", startTime="
				+ startTime + ", endTime=" + endTime + ", status=" + status + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + "]";
	}   
}