package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import com.flowiee.app.danhmuc.repository.LoaiKichCoRepository;
import com.flowiee.app.danhmuc.service.LoaiKichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiKichCoServiceImpl implements LoaiKichCoService {
    @Autowired
    private LoaiKichCoRepository loaiKichCoRepository;

    @Override
    public List<LoaiKichCo> findAll() {
        return loaiKichCoRepository.findAll();
    }

    @Override
    public LoaiKichCo findById(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return loaiKichCoRepository.findById(id).orElse(null);
    }

    @Override
    public String save(LoaiKichCo loaiKichCo) {
        try {
            loaiKichCoRepository.save(loaiKichCo);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(LoaiKichCo loaiKichCo, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            loaiKichCoRepository.save(loaiKichCo);
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
        loaiKichCoRepository.deleteById(id);
        if (this.findById(id) == null) {
            return "OK";
        }
        return "NOK";
    }
}