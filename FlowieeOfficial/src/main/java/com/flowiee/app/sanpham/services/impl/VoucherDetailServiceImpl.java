package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.VoucherDetail;
import com.flowiee.app.sanpham.repository.VoucherDetailRepository;
import com.flowiee.app.sanpham.repository.VoucherRepository;
import com.flowiee.app.sanpham.services.VoucherDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherDetailServiceImpl implements VoucherDetailService {
    @Autowired
    private VoucherDetailRepository voucherDetailRepository;

    @Override
    public List<VoucherDetail> findAll() {
        return voucherDetailRepository.findAll();
    }

    @Override
    public VoucherDetail findById(Integer voucherDetailId) {
        return voucherDetailRepository.findById(voucherDetailId).orElse(null);
    }

    @Override
    public String save(VoucherDetail voucherDetail) {
        voucherDetailRepository.save(voucherDetail);
        return "OK";
    }

    @Override
    public String update(VoucherDetail voucherDetail, Integer voucherDetailId) {
        return null;
    }

    @Override
    public String detele(Integer voucherDetailId) {
        return null;
    }
}