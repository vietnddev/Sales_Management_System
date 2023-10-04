package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.Voucher;
import com.flowiee.app.sanpham.model.VoucherResponse;

import java.util.List;

public interface VoucherService {
    List<VoucherResponse> findAll();

    Voucher findById(Integer voucherId);

    String save(Voucher voucher, List<Integer> listSanPhamApDung);

    String update(Voucher voucher, Integer voucherId);

    String detele(Integer voucherId);
}