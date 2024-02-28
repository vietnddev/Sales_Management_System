package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.dto.DocumentDTO;
import com.flowiee.app.entity.Document;
import com.flowiee.app.dto.DocMetaDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentService extends BaseService<Document> {
    Page<Document> findDocuments(Integer pageSize, Integer pageNum, Integer parentId);

    List<Document> findDocumentByParentId(Integer parentId);

    List<DocumentDTO> findFolderByParentId(Integer parentId);

    List<Document> findFileByParentId(Integer parentId);

    List<Document> findAllFolder();

    String updateMetadata(List<DocMetaDTO> metaDTOs, Integer documentId);

    List<DocMetaDTO> findMetadata(Integer documentId);

    List<Document> findByDoctype(Integer docType);

    DocumentDTO save(DocumentDTO documentDTO);

    DocumentDTO update(DocumentDTO documentDTO, Integer documentId);

    List<DocumentDTO> findHierarchyOfDocument(Integer documentId, Integer parentId);

    List<DocumentDTO> generateFolderTree(Integer parentId);
}