package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.repository.ChiTietDonHangRepository;
import com.flowiee.app.sanpham.services.ChiTietDonHangService;
import com.flowiee.app.sanpham.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDonHangRepository chiTietDonHangRepository;

    @Override
    public List<DonHangChiTiet> findAll() {
        return chiTietDonHangRepository.findAll();
    }

    @Override
    public DonHangChiTiet findById(int id) {
        return chiTietDonHangRepository.findById(id).orElse(null);
    }

    @Override
    public List<DonHangChiTiet> findByDonHangId(int donHangId) {
        if (donHangId <= 0 || donHangService.findById(donHangId) == null) {
            throw new NotFoundException();
        }
        return chiTietDonHangRepository.findByDonHangId(donHangService.findById(donHangId));
    }

    @Override
    @Transactional
    public String save(DonHangChiTiet donHangChiTiet) {
        if (donHangChiTiet.getDonHang() == null) {
            throw new NotFoundException();
        }
        try {
            chiTietDonHangRepository.save(donHangChiTiet);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(DonHangChiTiet donHangChiTiet, int id) {
        if (id <=0 || this.findById(id) == null || donHangChiTiet.getDonHang() == null) {
            throw new NotFoundException();
        }
        try {
            donHangChiTiet.setId(id);
            chiTietDonHangRepository.save(donHangChiTiet);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String delete(int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        try {
            chiTietDonHangRepository.deleteById(id);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }
}