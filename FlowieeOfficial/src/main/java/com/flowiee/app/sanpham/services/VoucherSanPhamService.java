package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.VoucherSanPham;

import java.util.List;

public interface VoucherSanPhamService {
    VoucherSanPham findById(Integer id);

    List<VoucherSanPham> findAll();

    List<VoucherSanPham> findByVoucherId(Integer voucherId);

    String save(VoucherSanPham voucherSanPham);

    String update(VoucherSanPham voucherSanPham, Integer id);

    String detele(Integer id);
}