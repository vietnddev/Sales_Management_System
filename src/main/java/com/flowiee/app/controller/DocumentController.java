package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.DocumentDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/stg")
public class DocumentController extends BaseController {
    private final DocumentService documentService;
    private final DocFieldService docFieldService;

    public DocumentController(DocumentService documentService, DocFieldService docFieldService) {
        this.documentService = documentService;
        this.docFieldService = docFieldService;
    }

    @Operation(summary = "Find all documents")
    @GetMapping("/doc/all")
    public ApiResponse<List<DocumentDTO>> getAllDocuments(@RequestParam("pageSize") Integer pageSize,
                                                          @RequestParam("pageNum") Integer pageNum,
                                                          @RequestParam("parentId") Integer parentId) {
        try {
            if (!super.vldModuleStorage.readDoc(true)) {
                return null;
            }
            Page<Document> documents = documentService.findDocuments(pageSize, pageNum - 1, parentId);
            return ApiResponse.ok(DocumentDTO.fromDocuments(documents.getContent()), pageNum, pageSize, documents.getTotalPages(), documents.getTotalElements());
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "documents"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "documents"));
        }
    }

    @Operation(summary = "Create new document")
    @PostMapping("/doc/create")
    public ApiResponse<DocumentDTO> insertNewDoc(@RequestParam(value = "fileUpload", required = false) MultipartFile fileUpload,
                                                 @RequestParam(value = "docTypeId", required = false) Integer docTypeId,
                                                 @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "description", required = false) String description,
                                                 @RequestParam(value = "isFolder") String isFolder,
                                                 @RequestParam(value = "parentId") Integer parentId) {
        try {
            if (!super.vldModuleStorage.insertDoc(true)) {
                return null;
             }
            DocumentDTO document = new DocumentDTO();
            document.setParentId(parentId);
            document.setName(name);
            document.setDescription(description);
            document.setIsFolder(isFolder);
            document.setDocTypeId(docTypeId);
            document.setFileUpload(fileUpload);
            return ApiResponse.ok(documentService.save(document));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "document"), ex);
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "document"), ex);
        }
    }

    @Operation(summary = "Find all folders")
    @GetMapping("/doc/folders")
    public ApiResponse<List<DocumentDTO>> getAllFolders(@RequestParam(value = "parentId", required = false) Integer parentId) {
        try {
            if (!super.vldModuleStorage.readDoc(true)) {
                return null;
            }
            return ApiResponse.ok(documentService.findFolderByParentId(parentId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "folders"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "folders"));
        }
    }

    @DeleteMapping("/doc/doc-field/delete/{id}")
    public ApiResponse<String> deleteDocField(@PathVariable("id") Integer docFiledId) {
        vldModuleStorage.deleteDoc(true);
        if (docFieldService.findById(docFiledId) == null) {
            throw new NotFoundException("DocField not found!");
        }
        return ApiResponse.ok(docFieldService.delete(docFiledId));
    }
}