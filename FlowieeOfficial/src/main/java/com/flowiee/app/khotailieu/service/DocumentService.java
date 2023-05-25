package com.flowiee.app.khotailieu.service;

import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public List<Document> findRootDocument() {
        return documentRepository.findRootDocument();
    }

    public List<Document> findListDocument(int parentId) {
        return documentRepository.findListDocument(parentId);
    }

    public Document findById(int id) {
        return documentRepository.findById(id).orElse(null);
    }

    public Document save(Document document) {
        return documentRepository.save(document);
    }

    public Document update(Document document) {
        return documentRepository.save(document);
    }

    public String delete(int id) {
        documentRepository.deleteById(id);
        if (findById(id) == null) {
            return "OK";
        } else {
            return "NOK";
        }
    }
}
