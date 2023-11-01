package com.flowiee.app.product.model;

import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.product.entity.VoucherDetail;
import lombok.Data;

import java.util.List;

@Data
public class VoucherResponse {
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