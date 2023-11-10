package com.flowiee.app.model.product;

import lombok.Data;

import java.util.List;

@Data
public class TopSanPhamBanChay {
    private List<String> tenSanPham;
    private List<Integer> soLuongDaBan;
}