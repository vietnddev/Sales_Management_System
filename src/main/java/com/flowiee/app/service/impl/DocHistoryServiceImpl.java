package com.flowiee.app.service.impl;

import com.flowiee.app.entity.DocHistory;
import com.flowiee.app.repository.DocHistoryRepository;
import com.flowiee.app.service.DocHistoryService;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocHistoryServiceImpl implements DocHistoryService {
    @Autowired
    private DocHistoryRepository docHistoryRepo;

    @Override
    public List<DocHistory> findAll() {
        return docHistoryRepo.findAll();
    }

    @Override
    public DocHistory findById(Integer docHistoryId) {
        return docHistoryRepo.findById(docHistoryId).orElse(null);
    }

    @Override
    public DocHistory save(DocHistory docHistory) {
        return docHistoryRepo.save(docHistory);
    }

    @Override
    public DocHistory update(DocHistory docHistory, Integer docHistoryId) {
        docHistory.setId(docHistoryId);
        return docHistoryRepo.save(docHistory);
    }

    @Override
    public String delete(Integer docHistoryId) {
        docHistoryRepo.deleteById(docHistoryId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<DocHistory> findByDocument(Integer documentId) {
        return docHistoryRepo.findByDocument(documentId);
    }

    @Override
    public List<DocHistory> findByDocData(Integer docDataId) {
        return findByDocData(docDataId);
    }
}