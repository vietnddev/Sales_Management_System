package com.flowiee.app.dashboard.model;

import lombok.Data;

import java.util.List;

@Data
public class DoanhThuCacNgayTheoThang {
    private List<String> listNgay;
    private List<Float> listDoanhThu;
}
