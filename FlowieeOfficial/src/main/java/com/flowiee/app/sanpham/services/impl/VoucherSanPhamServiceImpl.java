package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.Voucher;
import com.flowiee.app.sanpham.entity.VoucherDetail;
import com.flowiee.app.sanpham.entity.VoucherSanPham;
import com.flowiee.app.sanpham.model.VoucherResponse;
import com.flowiee.app.sanpham.repository.VoucherRepository;
import com.flowiee.app.sanpham.repository.VoucherSanPhamRepository;
import com.flowiee.app.sanpham.services.VoucherDetailService;
import com.flowiee.app.sanpham.services.VoucherSanPhamService;
import com.flowiee.app.sanpham.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherSanPhamServiceImpl implements VoucherSanPhamService {
    @Autowired
    private VoucherSanPhamRepository voucherSanPhamRepository;

    @Override
    public List<VoucherSanPham> findAll() {
        return voucherSanPhamRepository.findAll();
    }

    @Override
    public List<VoucherSanPham> findByVoucherId(Integer voucherId) {
        return voucherSanPhamRepository.findByVoucherId(voucherId);
    }

    @Override
    public VoucherSanPham findById(Integer id) {
        return voucherSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public String save(VoucherSanPham voucherSanPham) {
        voucherSanPhamRepository.save(voucherSanPham);
        return "OK";
    }

    @Override
    public String update(VoucherSanPham voucherSanPham, Integer id) {
        if (this.findById(id) != null) {
            voucherSanPham.setId(id);
            voucherSanPhamRepository.save(voucherSanPham);
        }
        return "OK";
    }

    @Override
    public String detele(Integer id) {
        if (this.findById(id) != null) {
            voucherSanPhamRepository.deleteById(id);
        }
        return "OK";
    }
}