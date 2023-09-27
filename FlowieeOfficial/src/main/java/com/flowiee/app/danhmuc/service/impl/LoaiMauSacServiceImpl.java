package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import com.flowiee.app.danhmuc.repository.LoaiMauSacRepository;
import com.flowiee.app.danhmuc.service.LoaiMauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LoaiMauSacServiceImpl implements LoaiMauSacService {
    @Autowired
    private LoaiMauSacRepository loaiMauSacRepository;

    @Override
    public List<LoaiMauSac> findAll() {
        return loaiMauSacRepository.findAll();
    }

    @Override
    public LoaiMauSac findById(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return loaiMauSacRepository.findById(id).orElse(null);
    }

    @Override
    public String save(LoaiMauSac loaiMauSac) {
        try {
            loaiMauSacRepository.save(loaiMauSac);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(LoaiMauSac loaiMauSac, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            loaiMauSacRepository.save(loaiMauSac);
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
        loaiMauSacRepository.deleteById(id);
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