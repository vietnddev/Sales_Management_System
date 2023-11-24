package com.flowiee.app.service;

import com.flowiee.app.entity.Voucher;
import com.flowiee.app.dto.VoucherDTO;

import java.util.List;

public interface VoucherService {
    List<VoucherDTO> findAll();

    Voucher findById(Integer voucherId);

    String save(Voucher voucher, List<Integer> listSanPhamApDung);

    String update(Voucher voucher, Integer voucherId);

    String detele(Integer voucherId);
}