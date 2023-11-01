package com.flowiee.app.product.services;

import com.flowiee.app.product.entity.Voucher;
import com.flowiee.app.product.model.VoucherResponse;

import java.util.List;

public interface VoucherService {
    List<VoucherResponse> findAll();

    Voucher findById(Integer voucherId);

    String save(Voucher voucher, List<Integer> listSanPhamApDung);

    String update(Voucher voucher, Integer voucherId);

    String detele(Integer voucherId);
}