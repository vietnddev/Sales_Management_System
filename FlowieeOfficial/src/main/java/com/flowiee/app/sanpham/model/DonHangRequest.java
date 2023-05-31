package com.flowiee.app.sanpham.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DonHangRequest implements Serializable {
    private List<Integer> listBienTheSanPham;
    private int nhanVienBanHang;
    private int khachHang;
    private int kenhBanHang;
    private String diaChiGiaoHang;
    private Date thoiGianDatHang;
    private String ghiChu;
}
