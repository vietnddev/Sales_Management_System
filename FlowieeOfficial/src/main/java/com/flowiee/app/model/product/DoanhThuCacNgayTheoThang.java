package com.flowiee.app.model.product;

import lombok.Data;

import java.util.List;

@Data
public class DoanhThuCacNgayTheoThang {
    private List<String> listNgay;
    private List<Float> listDoanhThu;
}
