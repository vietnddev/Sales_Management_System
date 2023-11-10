package com.flowiee.app.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.entity.DocData;
import com.flowiee.app.repository.storage.DocDataRepository;
import com.flowiee.app.service.storage.DocDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocDataServiceImpl implements DocDataService {
    @Autowired
    private DocDataRepository docDataRepository;

    public List<DocData> findAll() {
        return docDataRepository.findAll();
    }

    public DocData findById(int id) {
        return docDataRepository.findById(id).orElse(null);
    }

    public DocData save(DocData docData) {
        return docDataRepository.save(docData);
    }

    public DocData delete(int id) {
        DocData docDataToDelete = findById(id);
        if (docDataToDelete != null) {
            docDataRepository.deleteById(id);
            return docDataToDelete;
        } else {
            throw new NotFoundException();
        }
    }
}