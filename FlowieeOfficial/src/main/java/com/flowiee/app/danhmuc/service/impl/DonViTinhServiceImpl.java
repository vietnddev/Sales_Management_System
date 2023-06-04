package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.DonViTinh;
import com.flowiee.app.danhmuc.repository.DonViTinhRepository;
import com.flowiee.app.danhmuc.service.DonViTinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonViTinhServiceImpl implements DonViTinhService {
    @Autowired
    private DonViTinhRepository donViTinhRepository;

    @Override
    public List<DonViTinh> findAll() {
        return donViTinhRepository.findAll();
    }

    @Override
    public DonViTinh findById(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return donViTinhRepository.findById(id).orElse(null);
    }

    @Override
    public String save(DonViTinh donViTinh) {
        try {
            donViTinhRepository.save(donViTinh);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(DonViTinh donViTinh, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            donViTinh.setId(id);
            donViTinhRepository.save(donViTinh);
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
        donViTinhRepository.deleteById(id);
        if (this.findById(id) == null) {
            return "OK";
        }
        return "NOK";
    }
}
