package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.sanpham.entity.DonHangThanhToan;
import com.flowiee.app.sanpham.repository.DonHangThanhToanRepository;
import com.flowiee.app.sanpham.services.DonHangThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangThanhToanServiceImpl implements DonHangThanhToanService {
    @Autowired
    private DonHangThanhToanRepository donHangThanhToanRepository;

    @Override
    public List<DonHangThanhToan> findAll() {
        return donHangThanhToanRepository.findAll();
    }

    @Override
    public List<DonHangThanhToan> findByDonHangId(int id) {
        return donHangThanhToanRepository.findByDonHangId(id);
    }

    @Override
    public DonHangThanhToan findById(int id) {
        return donHangThanhToanRepository.findById(id).orElse(null);
    }

    @Override
    public String save(DonHangThanhToan donHangThanhToan) {
        if (donHangThanhToan.getDonHang().getId() <= 0) {
            throw new BadRequestException();
        }
        try {
            donHangThanhToanRepository.save(donHangThanhToan);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }

    @Override
    public String update(DonHangThanhToan donHangThanhToan, int id) {
        if (this.findById(id) == null || donHangThanhToan.getHinhThucThanhToan() == null) {
            throw new NotFoundException();
        }
        try {
            donHangThanhToan.setId(id);
            donHangThanhToanRepository.save(donHangThanhToan);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }

    @Override
    public String delete(int id) {
        if (this.findById(id) == null) {
            throw new NotFoundException();
        }
        try {
            donHangThanhToanRepository.deleteById(id);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }
}