package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.VoucherSanPham;
import com.flowiee.app.repository.product.VoucherSanPhamRepository;
import com.flowiee.app.service.product.VoucherSanPhamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(VoucherSanPham voucherSanPham, Integer id) {
        if (this.findById(id) != null) {
            voucherSanPham.setId(id);
            voucherSanPhamRepository.save(voucherSanPham);
        }
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId) != null) {
            voucherSanPhamRepository.deleteById(entityId);
        }
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}