package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import com.flowiee.app.danhmuc.repository.TrangThaiDonHangRepository;
import com.flowiee.app.danhmuc.service.TrangThaiDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TrangThaiDonHangServiceImpl implements TrangThaiDonHangService {
    @Autowired
    private TrangThaiDonHangRepository trangThaiDonHangRepository;

    @Override
    public List<TrangThaiDonHang> findAll() {
        return trangThaiDonHangRepository.findAll();
    }

    @Override
    public TrangThaiDonHang findById(int id) {
        return trangThaiDonHangRepository.findById(id).orElse(null);
    }

    @Override
    public String save(TrangThaiDonHang trangThaiDonHang) {
        try {
            trangThaiDonHangRepository.save(trangThaiDonHang);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(TrangThaiDonHang trangThaiDonHang, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            trangThaiDonHangRepository.save(trangThaiDonHang);
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
            trangThaiDonHangRepository.deleteById(id);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String importData(MultipartFile fileImport) {
        return null;
    }

    @Override
    public byte[] exportData() {
        return new byte[0];
    }
}