package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.model.TrangThaiDonHang;
import com.flowiee.app.sanpham.repository.DonHangRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;

    @Override
    public List<DonHang> findAll() {
        return null;
    }

    @Override
    public List<DonHang> findByTrangThai(String status) {
        return null;
    }

    @Override
    public List<DonHang> search() {
        return null;
    }

    @Override
    public DonHang findById(int id) {
        return null;
    }

    @Override
    public DonHang save(DonHangRequest request) {
        try {
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(FlowieeUtil.maDonHang());
            donHang.setKhachHang(KhachHang.builder().id(request.getKhachHang()).build());
            donHang.setKenhBanHang(KenhBanHang.builder().id(request.getKenhBanHang()).build());
            donHang.setNhanVienBanHang(Account.builder().id(request.getNhanVienBanHang()).build());
            donHang.setGhiChu(request.getGhiChu());
            donHang.setThoiGianDatHang(request.getThoiGianDatHang());
            donHang.setTrangThaiDonHang(request.getTrangThaiDonHang());
            donHangRepository.save(donHang);

            for (int idBienThe : request.getListBienTheSanPham()) {
                DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                donHangChiTiet.setDonHang(donHang);
                donHangChiTiet.setBienTheSanPham(bienTheSanPhamService.findById(idBienThe));
                donHangChiTiet.setGhiChu("");
                donHangChiTiet.setSoLuong(1);
                donHangChiTiet.setTrangThai(true);
            }
            return donHang;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonHang update(DonHang donHang, int id) {
        return null;
    }

    @Override
    public String delete(int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        donHangRepository.deleteById(id);
        if (this.findById(id) == null) {
            return "OK";
        }
        return "NOK";
    }
}