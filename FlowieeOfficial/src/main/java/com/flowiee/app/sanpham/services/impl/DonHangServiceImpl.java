package com.flowiee.app.sanpham.services.impl;

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

import java.util.Date;
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
    public DonHang findById(int id) {
        return null;
    }

    @Override
    public DonHang save(DonHangRequest request) {
        DonHang donHang = new DonHang();
        donHang.setMaDonHang(FlowieeUtil.maDonHang());
        donHang.setKhachHang(KhachHang.builder().id(request.getKhachHang()).build());
        donHang.setKenhBanHang(KenhBanHang.builder().id(request.getKenhBanHang()).build());
        donHang.setNhanVienBanHang(Account.builder().id(request.getNhanVienBanHang()).build());
        donHang.setGhiChu(request.getGhiChu());
        donHang.setThoiGianDatHang(request.getThoiGianDatHang());
        donHang.setTrangThai(TrangThaiDonHang.CHUA_XAC_NHAN.name());
        donHangRepository.save(donHang);

        for (int idBienThe : request.getListBienTheSanPham()) {
            DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
            donHangChiTiet.setDonHang(donHang);
            donHangChiTiet.setBienTheSanPham(bienTheSanPhamService.findById(idBienThe));
            donHangChiTiet.setTrangThai(true);


        }



        return donHang;
    }

    @Override
    public DonHang update(DonHang donHang) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}