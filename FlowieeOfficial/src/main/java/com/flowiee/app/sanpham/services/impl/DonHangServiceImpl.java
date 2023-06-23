package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import com.flowiee.app.danhmuc.service.TrangThaiDonHangService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.repository.DonHangRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.ChiTietDonHangService;
import com.flowiee.app.sanpham.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository donHangRepository;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;
    @Autowired
    private TrangThaiDonHangService trangThaiDonHangService;

    @Override
    public List<DonHang> findAll() {
        return donHangRepository.findAll();
    }

    @Override
    public List<DonHang> findAll(String searchTxt, String thoiGianDatHang,
                                 int kenhBanHangId, int trangThaiDonHangId) {
        return donHangRepository.findAll(searchTxt, kenhBanHangId, trangThaiDonHangId);
    }

    @Override
    public List<DonHang> findByTrangThai(int trangThaiDonHangId) {
        return donHangRepository.findByTrangThaiDonHang(trangThaiDonHangService.findById(trangThaiDonHangId));
    }

    @Override
    public List<DonHang> search() {
        return null;
    }

    @Override
    public List<DonHang> findByKhachHangId(int khachHangId) {
        return donHangRepository.findByKhachHangId(khachHangId);
    }

    @Override
    public List<DonHang> findByNhanVienId(int nhanVienId) {
        return donHangRepository.findByNhanvienId(nhanVienId);
    }

    @Override
    public DonHang findById(int id) {
        return donHangRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public DonHang save(DonHangRequest request) {
        try {
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(FlowieeUtil.maDonHang());
            donHang.setKhachHang(KhachHang.builder().id(request.getKhachHang()).build());
            donHang.setKenhBanHang(KenhBanHang.builder().id(request.getKenhBanHang()).build());
            donHang.setNhanVienBanHang(Account.builder().id(request.getNhanVienBanHang()).build());
            donHang.setGhiChu(request.getGhiChu());
            donHang.setThoiGianDatHang(request.getThoiGianDatHang());
            donHang.setTrangThaiDonHang(TrangThaiDonHang.builder().id(request.getTrangThaiDonHang()).build());
            donHang.setTongTienDonHang(0D);
            donHangRepository.save(donHang);

            DonHang donHangSaved = donHangRepository.findDonHangMoiNhat().get(0);

            Double totalMoneyOfDonHang = 0D;
            for (int idBienTheSP : request.getListBienTheSanPham()) {
                DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                donHangChiTiet.setDonHang(donHangSaved);
                donHangChiTiet.setBienTheSanPham(bienTheSanPhamService.findById(idBienTheSP));
                donHangChiTiet.setGhiChu("");
                donHangChiTiet.setSoLuong(1);
                donHangChiTiet.setTrangThai(true);
                chiTietDonHangService.save(donHangChiTiet);
                totalMoneyOfDonHang += bienTheSanPhamService.getGiaBan(idBienTheSP);
            }
            donHangSaved.setTongTienDonHang(totalMoneyOfDonHang);
            donHangRepository.save(donHangSaved);

            return donHangSaved;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DonHang update(DonHang donHang, int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        donHang.setId(id);
        return donHangRepository.save(donHang);
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