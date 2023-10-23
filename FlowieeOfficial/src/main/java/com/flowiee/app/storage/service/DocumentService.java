package com.flowiee.app.storage.service;

import com.flowiee.app.storage.entity.Document;
import com.flowiee.app.storage.model.DocMetaResponse;

import java.util.List;

public interface DocumentService {

    List<Document> findRootDocument();

    List<Document> findRootFolder();

    List<Document> findRootFile();

    List<Document> findDocumentByParentId(int parentId);

    List<Document> findFolderByParentId(int parentId);

    List<Document> findFileByParentId(int parentId);

    List<Document> findAllFolder();

    Document findById(int id);

    Document save(Document document);

    String update(Document document, int documentId);

    String updateMetadata(Integer[] docDataIds, String[] docDataValues, int documentId);

    String delete(int id);

    List<DocMetaResponse> getMetadata(int documentId);

    //void getCayThuMucSTG();
}