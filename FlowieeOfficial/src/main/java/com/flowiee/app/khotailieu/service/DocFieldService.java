package com.flowiee.app.khotailieu.service;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.khotailieu.entity.DocField;

import java.util.List;

public interface DocFieldService {

    List<DocField> findAll();

    DocField findById(Integer id);

    List<DocField> findByDocTypeId(LoaiTaiLieu loaiTaiLieu);

    String save(DocField docField);

    String update(DocField docField, Integer docFieldId);

    DocField delete(Integer id);
}
