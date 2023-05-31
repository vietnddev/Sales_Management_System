package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.TrangThaiGiaoHang;
import com.flowiee.app.danhmuc.repository.TrangThaiGiaoHangRepository;
import com.flowiee.app.danhmuc.service.TrangThaiGiaoHangService;
import com.flowiee.app.sanpham.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrangThaiGiaoHangServiceImpl implements TrangThaiGiaoHangService {
    @Autowired
    private TrangThaiGiaoHangRepository trangThaiGiaoHangRepository;
    @Autowired
    private DonHangService donHangService;

    @Override
    public List<TrangThaiGiaoHang> findAll() {
        return trangThaiGiaoHangRepository.findAll();
    }

    @Override
    public List<TrangThaiGiaoHang> findByDonHangId(int donHangId) {
        if (donHangService.findById(donHangId) == null) {
            throw new NotFoundException();
        }
        return trangThaiGiaoHangRepository.findByDonHangId(donHangService.findById(donHangId));
    }

    @Override
    public TrangThaiGiaoHang findById(int id) {
        return trangThaiGiaoHangRepository.findById(id).orElse(null);
    }

    @Override
    public String save(TrangThaiGiaoHang trangThaiGiaoHang) {
        try {
            trangThaiGiaoHangRepository.save(trangThaiGiaoHang);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(TrangThaiGiaoHang trangThaiGiaoHang, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            trangThaiGiaoHangRepository.save(trangThaiGiaoHang);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String delete(int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            trangThaiGiaoHangRepository.deleteById(id);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }
}
