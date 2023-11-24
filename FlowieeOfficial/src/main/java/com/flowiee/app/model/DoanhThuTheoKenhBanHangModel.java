package com.flowiee.app.model;

import lombok.Data;

import java.util.List;

@Data
public class DoanhThuTheoKenhBanHangModel {
    private List<String> tenOfKenh;
    private List<Float> doanhThuOfKenh;
    private List<String> mauSac;
}