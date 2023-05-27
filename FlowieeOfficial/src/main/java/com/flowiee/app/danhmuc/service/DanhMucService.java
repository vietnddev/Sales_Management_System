package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.DanhMuc;

import java.util.List;

public interface DanhMucService {

    List<DanhMuc> getListRootCategory();

    List<DanhMuc> getListCategory(String code);

    String getNameItem(String code, String type);

     void insertCategory(DanhMuc category);

     void update(DanhMuc category, int id);

     void delete(int id);
}