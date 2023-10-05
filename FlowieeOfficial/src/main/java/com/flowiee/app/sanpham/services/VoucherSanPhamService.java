package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.VoucherSanPham;

import java.util.List;

public interface VoucherSanPhamService {

    List<VoucherSanPham> findAll();

    VoucherSanPham findById(Integer id);

    String save(VoucherSanPham voucherSanPham);

    String update(VoucherSanPham voucherSanPham, Integer id);

    String detele(Integer id);
}