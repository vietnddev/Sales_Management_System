package com.flowiee.app.khotailieu.service;

import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.model.DocMetaResponse;

import java.util.List;

public interface DocumentService {

    List<Document> findRootDocument();

    List<Document> findListDocument(int parentId);

    Document findById(int id);

    Document save(Document document);

    String update(Document document, int documentId);

    String updateMetadata(Integer[] docDataIds, String[] docDataValues, int documentId);

    String delete(int id);

    List<DocMetaResponse> getMetadata(int documentId);

    //void getCayThuMucSTG();
}