package com.flowiee.app.dto;

import com.flowiee.app.entity.VoucherInfo;
import com.flowiee.app.entity.VoucherTicket;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import com.flowiee.app.entity.ProductVariant;

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
    private boolean status;
    private List<VoucherTicket> listVoucherTicket;
    private List<ProductVariant> listSanPhamApDung;

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
        voucherInfoDTO.setStatus(voucherInfo.isStatus());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        voucherInfoDTO.setStartTime(dateFormat.format(voucherInfo.getStartTime()));
        voucherInfoDTO.setEndTime(dateFormat.format(voucherInfo.getEndTime()));

        voucherInfoDTO.setListVoucherTicket(voucherInfo.getListVoucherTicket());

        return voucherInfoDTO;
    }
}