package com.flowiee.app.category.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.category.entity.TrangThaiDonHang;
import com.flowiee.app.category.repository.TrangThaiDonHangRepository;
import com.flowiee.app.category.service.TrangThaiDonHangService;
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
        if (trangThaiDonHang == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        try {
            trangThaiDonHangRepository.save(trangThaiDonHang);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(TrangThaiDonHang trangThaiDonHang, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            trangThaiDonHangRepository.save(trangThaiDonHang);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            trangThaiDonHangRepository.deleteById(id);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
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