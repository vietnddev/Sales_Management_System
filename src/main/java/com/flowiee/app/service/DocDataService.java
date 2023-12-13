package com.flowiee.app.service;

import java.util.List;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.DocData;

public interface DocDataService extends BaseService<DocData> {
    List<DocData> findByDocField(Integer docFieldId);
}