package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.DocMetaDTO;
import com.flowiee.app.dto.DocumentDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.ForbiddenException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
import com.flowiee.app.utils.PagesUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/storage")
public class DocumentController extends BaseController {
    private final DocumentService documentService;
    private final DocFieldService docFieldService;
    private final DocDataService docDataService;
    private final FileStorageService fileStorageService;
    private final DocShareService docShareService;
    private final CategoryService categoryService;

    public DocumentController(DocumentService documentService, DocFieldService docFieldService, DocDataService docDataService, FileStorageService fileStorageService, DocShareService docShareService, CategoryService categoryService) {
        this.documentService = documentService;
        this.docFieldService = docFieldService;
        this.docDataService = docDataService;
        this.fileStorageService = fileStorageService;
        this.docShareService = docShareService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Find all documents")
    @GetMapping("/root")
    public ApiResponse<List<DocumentDTO>> getAllDocuments(@RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
        try {
            if (!super.vldModuleStorage.readDoc(true)) {
                return null;
            }
            Page<Document> documents = documentService.findRootDocument(pageSize, pageNum - 1);
            return ApiResponse.ok(DocumentDTO.fromDocuments(documents.getContent()), pageNum, pageSize, documents.getTotalPages(), documents.getTotalElements());
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "documents"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "documents"));
        }
    }

    @Operation(summary = "Create new document")
    @PostMapping(value = "/document/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<DocumentDTO> insertNewDoc(@RequestParam("fileUpload") MultipartFile fileUpload,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("isFolder") String isFolder,
                                                 @RequestParam("docTypeId") Integer docTypeId
                                                 //,@RequestBody DocumentDTO document
                                                 ) {
        try {
            if (!super.vldModuleStorage.insertDoc(true)) {
                return null;
            }
            DocumentDTO document = new DocumentDTO();
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

    /* ****************** */


    //Dashboard
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

    //Màn hình root
    @GetMapping("/document")
    public ModelAndView getRootDocument() {
        vldModuleStorage.readDoc(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_DOCUMENT);
        List<Document> listRootDocument = documentService.findRootDocument(null, null).getContent();
        for (int i = 0; i < listRootDocument.size(); i++) {
            listRootDocument.get(i).setCreatedAt(listRootDocument.get(i).getCreatedAt());
        }
        modelAndView.addObject("listDocument", listRootDocument);
        modelAndView.addObject("document", new Document());
        //select-option danh sách loại tài liệu
        List<Category> listLoaiTaiLieu = new ArrayList<>();
        listLoaiTaiLieu.add(categoryService.findSubCategoryDefault(AppConstants.CATEGORY.DOCUMENT_TYPE.getName()));
        listLoaiTaiLieu.addAll(categoryService.findSubCategoryUnDefault(AppConstants.CATEGORY.DOCUMENT_TYPE.getName()));
        modelAndView.addObject("listLoaiTaiLieu", listLoaiTaiLieu);
        //select-option danh sách thư mục
        List<Document> listFolder = new ArrayList<>();
        listFolder.add(new Document(0, "--Chọn thư mục--"));
        listFolder.addAll(documentService.findAllFolder());
        modelAndView.addObject("listFolder", listFolder);
        //Parent name
        modelAndView.addObject("documentParentName", "KHO TÀI LIỆU");
        if (vldModuleStorage.insertDoc(false)) {
            modelAndView.addObject("action_create", "enable");
        }
        if (vldModuleStorage.updateDoc(false)) {
            modelAndView.addObject("action_update", "enable");
        }
        if (vldModuleStorage.deleteDoc(false)) {
            modelAndView.addObject("action_delete", "enable");
        }
        return baseView(modelAndView);
    }

    @GetMapping("/document/{aliasPath}")
    public ModelAndView getListDocument(@PathVariable("aliasPath") String aliasPath) {
        vldModuleStorage.readDoc(true);
        String aliasName = CommonUtils.getAliasNameFromAliasPath(aliasPath);
        int documentId = CommonUtils.getIdFromAliasPath(aliasPath);
        Document document = documentService.findById(documentId);
        if (!(aliasName + "-" + documentId).equals(document.getAsName() + "-" + document.getId())) {
            throw new NotFoundException("Document not found!");
        }
        if (!docShareService.isShared(documentId)) {
            throw new ForbiddenException(MessageUtils.ERROR_FORBIDDEN);
        }
        if (document.getIsFolder().equals(AppConstants.DOCUMENT_TYPE.FI.name())) {
            ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_DOCUMENT_DETAIL);
            modelAndView.addObject("docDetail", document);
            modelAndView.addObject("document", new Document());
            //load metadata
            List<DocMetaDTO> docMetaDTO = documentService.getMetadata(documentId);
            modelAndView.addObject("listDocDataInfo", docMetaDTO);
            //Load file active
            modelAndView.addObject("fileActiveOfDocument", fileStorageService.findFileIsActiveOfDocument(documentId));
            //Load các version khác của document
            modelAndView.addObject("listFileOfDocument", fileStorageService.getFileOfDocument(documentId));
            //Cây thư mục
            //modelAndView.addObject("folders", list);
            if (vldModuleStorage.updateDoc(false)) {
                modelAndView.addObject("action_update", "enable");
            }
            if (vldModuleStorage.deleteDoc(false)) {
                modelAndView.addObject("action_delete", "enable");
            }
            return baseView(modelAndView);
        }

        if (document.getIsFolder().equals(AppConstants.DOCUMENT_TYPE.FO.name())) {
            ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_DOCUMENT);
            modelAndView.addObject("document", new Document());
            modelAndView.addObject("listDocument", documentService.findDocumentByParentId(documentId));
            //select-option danh loại tài liệu
            List<Category> listLoaiTaiLieu = new ArrayList<>();
            listLoaiTaiLieu.add(categoryService.findSubCategoryDefault(AppConstants.CATEGORY.DOCUMENT_TYPE.getName()));
            listLoaiTaiLieu.addAll(categoryService.findSubCategoryUnDefault(AppConstants.CATEGORY.DOCUMENT_TYPE.getName()));
            modelAndView.addObject("listLoaiTaiLieu", listLoaiTaiLieu);
            //select-option danh sách thư mục
            List<Document> listFolder = new ArrayList<>();
            listFolder.add(documentService.findById(documentId));
            listFolder.addAll(documentService.findAllFolder());
            modelAndView.addObject("listFolder", listFolder);
            //Parent name
            modelAndView.addObject("documentParentName", document.getName().toUpperCase());
            if (vldModuleStorage.insertDoc(false)) {
                modelAndView.addObject("action_create", "enable");
            }
            if (vldModuleStorage.updateDoc(false)) {
                modelAndView.addObject("action_update", "enable");
            }
            if (vldModuleStorage.deleteDoc(false)) {
                modelAndView.addObject("action_delete", "enable");
            }

            return baseView(modelAndView);
        }

        return new ModelAndView();
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

    @GetMapping("/document/update-metadata/{id}")
    public ModelAndView updateMetadata(HttpServletRequest request,
                                       @PathVariable("id") Integer documentId,
                                       @RequestParam("docDataId") Integer[] docDataIds,
                                       @RequestParam("docDataValue") String[] docDataValues) {
        vldModuleStorage.updateDoc(true);
        if (documentId <= 0 || documentService.findById(documentId) == null) {
            throw new NotFoundException("Document not found!");
        }
        documentService.updateMetadata(docDataIds, docDataValues, documentId);
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

    @PostMapping("/docfield/insert")
    public ModelAndView insertDocfield(DocField docField, HttpServletRequest request) {
        vldModuleStorage.updateDoc(true);
        docField.setTrangThai(false);
        docFieldService.save(docField);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping(value = "/docfield/update/{id}", params = "update")
    public ModelAndView updateDocfield(HttpServletRequest request,
                                       @ModelAttribute("docField") DocField docField,
                                       @PathVariable("id") Integer docFieldId) {
        vldModuleStorage.updateDoc(true);
        if (docFieldId <= 0 || documentService.findById(docFieldId) == null) {
            throw new NotFoundException("Docfield not found!");
        }
        docFieldService.update(docField, docFieldId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/docfield/delete/{id}")
    public ModelAndView deleteDocfield(@PathVariable("id") Integer docfiledId, HttpServletRequest request) {
        vldModuleStorage.deleteDoc(true);
        if (docFieldService.findById(docfiledId) == null) {
            throw new NotFoundException("Docfield not found!");
        }
        docFieldService.delete(docfiledId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}