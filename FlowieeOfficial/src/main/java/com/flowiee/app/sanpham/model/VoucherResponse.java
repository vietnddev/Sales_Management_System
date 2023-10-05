package com.flowiee.app.sanpham.model;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.VoucherDetail;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
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
    private List<BienTheSanPham> listSanPhamApDung;
}