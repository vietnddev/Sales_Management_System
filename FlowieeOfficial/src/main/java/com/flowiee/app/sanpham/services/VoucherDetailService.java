package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.VoucherDetail;

import java.util.List;

public interface VoucherDetailService {
    List<VoucherDetail> findAll();

    List<VoucherDetail> findByVoucherId(Integer voucherId);

    VoucherDetail findById(Integer voucherDetailId);

    String save(VoucherDetail voucherDetail);

    String update(VoucherDetail voucherDetail, Integer voucherDetailId);

    String detele(Integer voucherDetailId);
}