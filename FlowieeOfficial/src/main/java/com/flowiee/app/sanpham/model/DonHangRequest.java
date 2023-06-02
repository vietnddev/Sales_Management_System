package com.flowiee.app.sanpham.model;

import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class DonHangRequest implements Serializable {
    private List<Integer> listBienTheSanPham;
    private int khachHang;
    private int kenhBanHang;
    private int hinhThucThanhToan;
    private String diaChiGiaoHang;
    private int nhanVienBanHang;
    private Date thoiGianDatHang;
    private int trangThaiDonHang;
    private String ghiChu;
    private String thoiGianDatHangSearch;
    private String searchTxt;
}
