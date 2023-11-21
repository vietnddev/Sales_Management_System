package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Document;
import com.flowiee.app.model.storage.DocMetaResponse;

import java.util.List;

public interface DocumentService extends BaseService<Document> {
    Document saveReturnEntity(Document document);

    List<Document> findRootDocument();

    List<Document> findRootFolder();

    List<Document> findRootFile();

    List<Document> findDocumentByParentId(Integer parentId);

    List<Document> findFolderByParentId(Integer parentId);

    List<Document> findFileByParentId(Integer parentId);

    List<Document> findAllFolder();

    String updateMetadata(Integer[] docDataIds, String[] docDataValues, Integer documentId);

    List<DocMetaResponse> getMetadata(Integer documentId);

    List<Document> findByDoctype(Integer docType);
}