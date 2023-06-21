package com.flowiee.app.dashboard.model;

import lombok.Data;

import java.util.List;

@Data
public class TopSanPhamBanChay {
    private List<String> tenSanPham;
    private List<Integer> soLuongDaBan;
}