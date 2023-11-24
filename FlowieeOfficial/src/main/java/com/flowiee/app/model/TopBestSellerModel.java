package com.flowiee.app.model;

import lombok.Data;

import java.util.List;

@Data
public class TopBestSellerModel {
    private List<String> tenSanPham;
    private List<Integer> soLuongDaBan;
}