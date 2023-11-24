package com.flowiee.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.entity.VoucherDetail;

@Data
public class VoucherDTO implements Serializable {
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
    private List<VoucherDetail> listVoucherDetail;
    private List<ProductVariant> listSanPhamApDung;
}