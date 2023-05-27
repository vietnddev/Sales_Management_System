package com.flowiee.app.khotailieu.service;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.khotailieu.entity.DocField;

import java.util.List;

public interface DocFieldService {

    List<DocField> findAll();

    DocField findById(int id);

    List<DocField> findByDocTypeId(LoaiTaiLieu loaiTaiLieu);

    DocField save(DocField docField);

    DocField delete(int id);
}
