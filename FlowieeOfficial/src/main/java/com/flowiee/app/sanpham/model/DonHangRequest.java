package com.flowiee.app.sanpham.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class DonHangRequest implements Serializable {
    private List<Integer> listBienTheSanPham;
    private Integer khachHang;
    private Integer kenhBanHang;
    private Integer hinhThucThanhToan;
    private String diaChiGiaoHang;
    private Integer nhanVienBanHang;
    private Date thoiGianDatHang;
    private Integer trangThaiDonHang;
    private Boolean trangThaiThanhToan;
    private String ghiChu;
    private String thoiGianDatHangSearch;
    private String searchTxt;
    private Integer cartId;
}