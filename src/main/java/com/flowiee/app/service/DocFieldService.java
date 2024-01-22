package com.flowiee.app.service;

import com.flowiee.app.entity.DocField;

import java.util.List;

public interface DocFieldService {

    List<DocField> findAll();

    DocField findById(Integer id);

    List<DocField> findByDocTypeId(Integer doctypeId);

    DocField save(DocField docField);

    DocField update(DocField docField, Integer docFieldId);

    DocField delete(Integer id);
}
