package com.flowiee.app.dto;

import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.VoucherInfo;
import com.flowiee.app.entity.VoucherTicket;
import com.flowiee.app.utils.AppConstants;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class VoucherInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String description;
    private String doiTuongApDung;
    private String voucherType;
    private Integer quantity;
    private Integer lengthOfKey;
    private Integer discount;
    private Float maxPriceDiscount;
    private String startTime;
    private String endTime;
    private String status;
    private List<VoucherTicket> listVoucherTicket;
    private List<Product> listSanPhamApDung;

    public static VoucherInfoDTO fromVoucherInfo(VoucherInfo voucherInfo) {
        VoucherInfoDTO voucherInfoDTO = new VoucherInfoDTO();
        voucherInfoDTO.setId(voucherInfo.getId());
        voucherInfoDTO.setTitle(voucherInfo.getTitle());
        voucherInfoDTO.setDescription(voucherInfo.getDescription());
        voucherInfoDTO.setDoiTuongApDung(voucherInfo.getDoiTuongApDung());
        voucherInfoDTO.setVoucherType(voucherInfo.getVoucherType());
        voucherInfoDTO.setQuantity(voucherInfo.getQuantity());
        voucherInfoDTO.setLengthOfKey(voucherInfo.getLengthOfKey());
        voucherInfoDTO.setDiscount(voucherInfo.getDiscount());
        voucherInfoDTO.setMaxPriceDiscount(voucherInfo.getMaxPriceDiscount());

        Date currentDate = new Date();
        if (voucherInfo.getStartTime().before(currentDate) && voucherInfo.getEndTime().after(currentDate)) {
            voucherInfoDTO.setStatus(AppConstants.VOUCHER_STATUS.ACTIVE.getLabel());
        } else {
            voucherInfoDTO.setStatus(AppConstants.VOUCHER_STATUS.INACTIVE.getLabel());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        voucherInfoDTO.setStartTime(dateFormat.format(voucherInfo.getStartTime()));
        voucherInfoDTO.setEndTime(dateFormat.format(voucherInfo.getEndTime()));

        voucherInfoDTO.setListVoucherTicket(voucherInfo.getListVoucherTicket());

        return voucherInfoDTO;
    }
}