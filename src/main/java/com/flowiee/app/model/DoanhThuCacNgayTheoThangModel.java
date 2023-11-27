package com.flowiee.app.model;

import lombok.Data;

import java.util.List;

@Data
public class DoanhThuCacNgayTheoThangModel {
    private List<String> listNgay;
    private List<Float> listDoanhThu;
}
