package com.flowiee.sms.model.dto;

import com.flowiee.sms.entity.VoucherInfo;
import com.flowiee.sms.entity.VoucherTicket;
import com.flowiee.sms.utils.AppConstants;
import com.flowiee.sms.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

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
public class VoucherInfoDTO extends VoucherInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer length;
    private BigDecimal discountPriceMax;
    private String status;
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
            voucherInfoDTO.setDiscountPriceMax(voucherInfo.getDiscountPriceMax());
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
		return "VoucherInfoDTO [id=" + id + ", maxPriceDiscount=" + discountPriceMax + ", startTime=" + getStartTime()
                + ", endTime=" + getEndTime() + ", status=" + status + ", createdAt=" + getCreatedAt() + ", createdBy=" + createdBy + "]";
	}   
}