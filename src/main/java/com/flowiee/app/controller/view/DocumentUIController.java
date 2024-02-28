package com.flowiee.app.controller.view;

import com.flowiee.app.dto.DocMetaDTO;
import com.flowiee.app.dto.DocumentDTO;
import com.flowiee.app.dto.FileDTO;
import com.flowiee.app.entity.Category;
import com.flowiee.app.exception.ForbiddenException;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.entity.Document;
import com.flowiee.app.service.DocFieldService;
import com.flowiee.app.service.DocShareService;
import com.flowiee.app.service.DocumentService;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.base.BaseController;

import com.flowiee.app.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/stg")
public class DocumentUIController extends BaseController {
    private final DocumentService documentService;
    private final DocFieldService docFieldService;
    private final FileStorageService fileStorageService;
    private final DocShareService docShareService;
    private final CategoryService categoryService;

    @Autowired
    public DocumentUIController(DocumentService documentService, DocFieldService docFieldService, FileStorageService fileStorageService,
                                DocShareService docShareService, CategoryService categoryService) {
        this.documentService = documentService;
        this.docFieldService = docFieldService;
        this.fileStorageService = fileStorageService;
        this.docShareService = docShareService;
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard")
    public ModelAndView showDashboardOfSTG() {
        vldModuleStorage.dashboard(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_DASHBOARD);
        //Loại tài liệu
        List<Category> listLoaiTaiLieu = categoryService.findSubCategory(AppConstants.CATEGORY.DOCUMENT_TYPE.getName(), null);
        List<String> listTenOfDocType = new ArrayList<>();
        List<Integer> listSoLuongOfDocType = new ArrayList<>();
        for (Category docType : listLoaiTaiLieu) {
            listTenOfDocType.add(docType.getName());
            listSoLuongOfDocType.add(docType.getListDocument() != null ? docType.getListDocument().size() : 0);
        }
        modelAndView.addObject("reportOfDocType_listTen", listTenOfDocType);
        modelAndView.addObject("reportOfDocType_listSoLuong", listSoLuongOfDocType);
        return baseView(modelAndView);
    }

    @GetMapping("/doc")
    public ModelAndView viewRootDocuments() {
        vldModuleStorage.readDoc(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_DOCUMENT);
        modelAndView.addObject("parentId", 0);
        modelAndView.addObject("folderTree", documentService.findFolderByParentId(0));
        return baseView(modelAndView);
    }

    @GetMapping("/doc/{aliasPath}")
    public ModelAndView viewSubDocuments(@PathVariable("aliasPath") String aliasPath) {
        vldModuleStorage.readDoc(true);
        String aliasName = CommonUtils.getAliasNameFromAliasPath(aliasPath);
        int documentId = CommonUtils.getIdFromAliasPath(aliasPath);
        Document document = documentService.findById(documentId);
        if (document == null || !(aliasName + "-" + documentId).equals(document.getAsName() + "-" + document.getId())) {
            throw new NotFoundException("Document not found!");
        }
        if (!docShareService.isShared(documentId)) {
            throw new ForbiddenException(MessageUtils.ERROR_FORBIDDEN);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("docBreadcrumb", documentService.findHierarchyOfDocument(document.getId(), document.getParentId()));
        modelAndView.addObject("folderTree", documentService.findFolderByParentId(0));
        modelAndView.addObject("documentParentName", document.getName());
        if (document.getIsFolder().equals("Y")) {
            modelAndView.setViewName(PagesUtils.STG_DOCUMENT);
            modelAndView.addObject("parentId", document.getId());
        }
        if (document.getIsFolder().equals("N")) {
            DocumentDTO docDTO = DocumentDTO.fromDocument(document);
            docDTO.setFile(FileDTO.fromFileStorage(fileStorageService.findFileIsActiveOfDocument(document.getId())));
            modelAndView.setViewName(PagesUtils.STG_DOCUMENT_DETAIL);
            modelAndView.addObject("docDetail", docDTO);
            modelAndView.addObject("docMeta", documentService.findMetadata(document.getId()));
        }
        return baseView(modelAndView);
    }

    @GetMapping("/doc/doc-type/{id}")
    public ModelAndView viewDocTypeDetail(@PathVariable("id") Integer docTypeId) {
        vldModuleStorage.updateDoc(true);
        Category docType = categoryService.findById(docTypeId);
        if (docType == null) {
            throw new NotFoundException("Document type not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_DOCTYPE_DETAIL);
        modelAndView.addObject("docTypeId", docTypeId);
        modelAndView.addObject("docFields", docFieldService.findByDocTypeId(docTypeId));
        return baseView(modelAndView);
    }

    @PostMapping("/document/change-file/{id}")
    public ModelAndView changeFile(@RequestParam("file") MultipartFile file,
                                   @PathVariable("id") Integer documentId,
                                   HttpServletRequest request) throws IOException {
        vldModuleStorage.updateDoc(true);
        if (documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        fileStorageService.changFileOfDocument(file, documentId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/document/update/{id}")
    public ModelAndView update(@ModelAttribute("document") Document document,
                               @PathVariable("id") Integer documentId, HttpServletRequest request) {
        vldModuleStorage.updateDoc(true);
        if (document == null || documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        documentService.update(document, documentId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/doc/update-metadata/{id}")
    public ModelAndView updateMetadata(HttpServletRequest request,
                                       @PathVariable("id") Integer documentId,
                                       @RequestParam("fieldId") Integer[] fieldId,
                                       @RequestParam("dataId") Integer[] dataId,
                                       @RequestParam("dataValue") String[] dataValue) {
        vldModuleStorage.updateDoc(true);
        if (documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        List<DocMetaDTO> metaDTOs = new ArrayList<>();
        for (int i = 0; i <fieldId.length; i++) {
            DocMetaDTO dto = new DocMetaDTO();
            dto.setFieldId(fieldId[i]);
            dto.setDataId(dataId[i]);
            dto.setDataValue(dataValue[i]);
            metaDTOs.add(dto);
            //System.out.println("fieldId " + fieldId[i] + ", data " + dataId[i] + ", value " + dataValue[i]);
        }
        documentService.updateMetadata(metaDTOs, documentId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/document/delete/{id}")
    public ModelAndView deleteDocument(@PathVariable("id") Integer documentId, HttpServletRequest request) {
        vldModuleStorage.deleteDoc(true);
        if (documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        documentService.delete(documentId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/document/move/{id}")
    public ModelAndView moveDocument(@PathVariable("id") Integer documentId, HttpServletRequest request) {
        vldModuleStorage.moveDoc(true);
        if (documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/document/share/{id}")
    public ModelAndView share(@PathVariable("id") Integer documentId, HttpServletRequest request) {
        vldModuleStorage.shareDoc(true);
        if (documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/doc/doc-field/create")
    public ModelAndView createDocField(DocField docField, HttpServletRequest request) {
        vldModuleStorage.updateDoc(true);
        docField.setRequired(docField.getRequired() != null ? docField.getRequired() : false);
        docField.setStatus(false);
        docFieldService.save(docField);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping(value = "/doc/doc-field/update/{id}", params = "update")
    public ModelAndView updateDocField(HttpServletRequest request, @ModelAttribute("docField") DocField docField, @PathVariable("id") Integer docFieldId) {
        vldModuleStorage.updateDoc(true);
        if (docFieldId <= 0 || docFieldService.findById(docFieldId) == null) {
            throw new NotFoundException("DocField not found!");
        }
        docField.setRequired(docField.getRequired() != null ? docField.getRequired() : false);
        docFieldService.update(docField, docFieldId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}