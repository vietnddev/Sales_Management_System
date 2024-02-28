package com.flowiee.app.service;

import java.util.List;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.DocData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocDataService extends BaseService<DocData> {
    List<DocData> findByDocField(Integer docFieldId);

    DocData findByFieldIdAndDocId(Integer docFieldId, Integer documentId);
}