package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.DocHistory;

import java.util.List;

public interface DocHistoryService extends BaseService<DocHistory> {
    List<DocHistory> findByDocument(Integer documentId);

    List<DocHistory> findByDocData(Integer docDataId);
}