package com.flowiee.app.khotailieu.service;

import com.flowiee.app.khotailieu.entity.Document;

import java.util.List;

public interface DocumentService {

    List<Document> findRootDocument();

    List<Document> findListDocument(int parentId);

    Document findById(int id);

    Document save(Document document);

    Document update(Document document);

    String delete(int id);
}