package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.danhmuc.repository.HinhThucThanhToanRepository;
import com.flowiee.app.danhmuc.repository.KenhBanHangRepository;
import com.flowiee.app.danhmuc.service.KenhBanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class KenhBanHangServiceImpl implements KenhBanHangService {
    @Autowired
    private KenhBanHangRepository kenhBanHangRepository;

    @Override
    public List<KenhBanHang> findAll() {
        return kenhBanHangRepository.findAll();
    }

    @Override
    public KenhBanHang findById(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return kenhBanHangRepository.findById(id).orElse(null);
    }

    @Override
    public String save(KenhBanHang kenhBanHang) {
        try {
            kenhBanHangRepository.save(kenhBanHang);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(KenhBanHang kenhBanHang, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            kenhBanHang.setId(id);
            kenhBanHangRepository.save(kenhBanHang);
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
        kenhBanHangRepository.deleteById(id);
        if (this.findById(id) == null) {
            return "OK";
        }
        return "NOK";
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
