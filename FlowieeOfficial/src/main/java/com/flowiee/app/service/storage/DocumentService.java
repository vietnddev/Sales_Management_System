package com.flowiee.app.service.storage;

import com.flowiee.app.entity.Document;
import com.flowiee.app.model.storage.DocMetaResponse;

import java.util.List;

public interface DocumentService {

    List<Document> findRootDocument();

    List<Document> findRootFolder();

    List<Document> findRootFile();

    List<Document> findDocumentByParentId(Integer parentId);

    List<Document> findFolderByParentId(Integer parentId);

    List<Document> findFileByParentId(Integer parentId);

    List<Document> findAllFolder();

    Document findById(Integer id);

    Document save(Document document);

    String update(Document document, Integer documentId);

    String updateMetadata(Integer[] docDataIds, String[] docDataValues, Integer documentId);

    String delete(Integer id);

    List<DocMetaResponse> getMetadata(Integer documentId);

    List<Document> findByDoctype(Integer docType);
}