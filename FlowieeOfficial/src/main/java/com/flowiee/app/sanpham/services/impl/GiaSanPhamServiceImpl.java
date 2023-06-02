package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;
import com.flowiee.app.sanpham.repository.GiaSanPhamRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.GiaSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaSanPhamServiceImpl implements GiaSanPhamService {
    @Autowired
    private GiaSanPhamRepository giaSanPhamRepository;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;

    @Override
    public List<GiaSanPham> findAll() {
        return giaSanPhamRepository.findAll();
    }

    @Override
    public List<GiaSanPham> findByBienTheSanPhamId(int bienTheSanPhamId) {
        return giaSanPhamRepository.findListGiaBanOfSP(bienTheSanPhamService.findById(bienTheSanPhamId));
    }

    @Override
    public GiaSanPham findById(int id) {
        return giaSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public Double findGiaHienTai(int bienTheSanPhamId) {
        return giaSanPhamRepository.findGiaBanHienTai(bienTheSanPhamService.findById(bienTheSanPhamId), true);
    }

    @Override
    public String save(GiaSanPham giaSanPham) {
        try {
            giaSanPhamRepository.save(giaSanPham);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(GiaSanPham giaSanPham, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            giaSanPhamRepository.save(giaSanPham);
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
        giaSanPhamRepository.deleteById(id);
        if (this.findById(id) == null) {
            return "OK";
        } else {
            return "NOK";
        }
    }
}