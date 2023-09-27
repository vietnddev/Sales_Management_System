package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.danhmuc.repository.HinhThucThanhToanRepository;
import com.flowiee.app.danhmuc.service.HinhThucThanhToanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class HinhThucThanhToanServiceImpl implements HinhThucThanhToanService {
    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Override
    public List<HinhThucThanhToan> findAll() {
        return hinhThucThanhToanRepository.findAll();
    }

    @Override
    public HinhThucThanhToan findById(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return hinhThucThanhToanRepository.findById(id).orElse(null);
    }

    @Override
    public String save(HinhThucThanhToan hinhThucThanhToan) {
        try {
            hinhThucThanhToanRepository.save(hinhThucThanhToan);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(HinhThucThanhToan hinhThucThanhToan, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            hinhThucThanhToanRepository.save(hinhThucThanhToan);
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
        hinhThucThanhToanRepository.deleteById(id);
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