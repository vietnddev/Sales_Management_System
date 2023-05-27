package com.flowiee.app.khotailieu.service.impl;

import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.repository.DocumentRepository;
import com.flowiee.app.khotailieu.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public List<Document> findRootDocument() {
        return documentRepository.findRootDocument();
    }

    @Override
    public List<Document> findListDocument(int parentId) {
        return documentRepository.findListDocument(parentId);
    }

    @Override
    public Document findById(int id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document save(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public Document update(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public String delete(int id) {
        documentRepository.deleteById(id);
        if (findById(id) == null) {
            return "OK";
        } else {
            return "NOK";
        }
    }
}