package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.DocField;

import java.util.List;

public interface DocFieldService extends BaseService<DocField> {
    List<DocField> findAll();

    List<DocField> findByDocTypeId(Integer doctypeId);
}