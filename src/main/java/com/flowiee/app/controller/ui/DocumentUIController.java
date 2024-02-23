package com.flowiee.app.controller.ui;

import com.flowiee.app.entity.Category;
import com.flowiee.app.exception.ForbiddenException;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.entity.Document;
import com.flowiee.app.dto.DocMetaDTO;
import com.flowiee.app.service.DocDataService;
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
@RequestMapping("/storage")
public class DocumentUIController extends BaseController {
    private final DocumentService documentService;
    private final DocFieldService docFieldService;
    private final DocDataService docDataService;
    private final FileStorageService fileStorageService;
    private final DocShareService docShareService;
    private final CategoryService categoryService;

    @Autowired
    public DocumentUIController(DocumentService documentService, DocFieldService docFieldService, DocDataService docDataService, FileStorageService fileStorageService, DocShareService docShareService, CategoryService categoryService) {
        this.documentService = documentService;
        this.docFieldService = docFieldService;
        this.docDataService = docDataService;
        this.fileStorageService = fileStorageService;
        this.docShareService = docShareService;
        this.categoryService = categoryService;
    }

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

    //Root screen
    @GetMapping("/document")
    public ModelAndView getRootDocument() {
        vldModuleStorage.readDoc(true);
        return baseView(new ModelAndView(PagesUtils.STG_DOCUMENT));
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

    //Insert FILE và FOLDER
//    @PostMapping("/document/insert")
//    public ModelAndView insert(HttpServletRequest request,
//                               @ModelAttribute("document") Document document,
//                               @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
//        validateModuleStorage.insertDoc(true);
//        document.setAsName(CommonUtils.generateAliasName(document.getName()));
//        document.setCreatedBy(CommonUtils.getCurrentAccountId());
//        if (document.getParentId() == null) {
//            document.setParentId(0);
//        }
//        Document documentSaved = documentService.saveReturnEntity(document);
//        //Trường hợp document được tạo mới là file upload
//        if (document.getIsFolder().equals(AppConstants.DOCUMENT_TYPE.FI.name()) && file != null) {
//            //Lưu file đính kèm vào thư mục chứ file upload
//            fileStorageService.saveFileOfDocument(file, documentSaved.getId());
//
//            //Lưu giá trị default vào DocData
//            List<DocField> listDocField = docFieldService.findByDocTypeId(document.getLoaiTaiLieu().getId());
//            for (DocField docField : listDocField) {
//                DocData docData = DocData.builder()
//                        .docField(new DocField(docField.getId()))
//                        .document(new Document(document.getId()))
//                        .noiDung("").build();
//                docDataService.save(docData);
//            }
//        }
//        return new ModelAndView("redirect:" + request.getHeader("referer"));
//    }

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