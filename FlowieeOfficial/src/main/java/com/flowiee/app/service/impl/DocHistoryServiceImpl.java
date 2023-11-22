package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.DocHistory;
import com.flowiee.app.repository.DocHistoryRepository;
import com.flowiee.app.service.DocHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocHistoryServiceImpl implements DocHistoryService {
    @Autowired
    private DocHistoryRepository docHistoryRepository;

    @Override
    public List<DocHistory> findAll() {
        return docHistoryRepository.findAll();
    }

    @Override
    public DocHistory findById(Integer docHistoryId) {
        return docHistoryRepository.findById(docHistoryId).orElse(null);
    }

    @Override
    public String save(DocHistory docHistory) {
        docHistoryRepository.save(docHistory);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(DocHistory docHistory, Integer docHistoryId) {
        docHistory.setId(docHistoryId);
        docHistoryRepository.save(docHistory);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer docHistoryId) {
        docHistoryRepository.deleteById(docHistoryId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<DocHistory> findByDocument(Integer documentId) {
        return docHistoryRepository.findByDocument(documentId);
    }

    @Override
    public List<DocHistory> findByDocData(Integer docDataId) {
        return findByDocData(docDataId);
    }
}