package com.flowiee.app.product.model;

import lombok.Data;

import java.util.List;

@Data
public class DoanhThuTheoKenhBanHang {
    private List<String> tenOfKenh;
    private List<Float> doanhThuOfKenh;
    private List<String> mauSac;
}