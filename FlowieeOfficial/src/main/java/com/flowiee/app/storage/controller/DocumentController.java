package com.flowiee.app.storage.controller;

import com.flowiee.app.config.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.config.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.*;
import com.flowiee.app.category.entity.LoaiTaiLieu;
import com.flowiee.app.category.service.LoaiTaiLieuService;
import com.flowiee.app.storage.service.FileStorageService;
import com.flowiee.app.system.service.MailService;
import com.flowiee.app.system.service.NotificationService;
import com.flowiee.app.storage.entity.DocData;
import com.flowiee.app.storage.entity.DocField;
import com.flowiee.app.storage.entity.Document;
import com.flowiee.app.storage.model.DocMetaResponse;
import com.flowiee.app.storage.model.DocumentType;
import com.flowiee.app.storage.repository.DocumentRepository;
import com.flowiee.app.storage.service.DocDataService;
import com.flowiee.app.storage.service.DocFieldService;
import com.flowiee.app.storage.service.DocShareService;
import com.flowiee.app.storage.service.DocumentService;
import com.flowiee.app.system.service.SystemLogService;
import com.flowiee.app.system.entity.Account;
import com.flowiee.app.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@CrossOrigin
@Controller
@RequestMapping("/kho-tai-lieu")
public class DocumentController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private DocFieldService docFieldService;
    @Autowired
    private DocDataService docDataService;
    @Autowired
    private LoaiTaiLieuService loaiTaiLieuService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModuleDanhMuc;
    @Autowired
    private KiemTraQuyenModuleKhoTaiLieu kiemTraQuyenModuleKhoTaiLieu;
    @Autowired
    private DocShareService docShareService;
    @Autowired
    private MailService mailService;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private NotificationService notificationService;

    //Dashboard
    @GetMapping("/dashboard")
    public ModelAndView showDashboardOfSTG() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleXemDashboard()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DASHBOARD);
            //Loại tài liệu
            List<LoaiTaiLieu> listLoaiTaiLieu = loaiTaiLieuService.findAll();
            List<String> listTenOfDocType = new ArrayList<>();
            List<Integer> listSoLuongOfDocType = new ArrayList<>();
            for (LoaiTaiLieu docType : listLoaiTaiLieu) {
                listTenOfDocType.add(docType.getTen());
                listSoLuongOfDocType.add(docType.getListDocument() != null ? docType.getListDocument().size() : 0);
            }
            modelAndView.addObject("reportOfDocType_listTen", listTenOfDocType);
            modelAndView.addObject("reportOfDocType_listSoLuong", listSoLuongOfDocType);
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    //Màn hình root
    @GetMapping("/document")
    public ModelAndView getRootDocument() {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DOCUMENT);
            List<Document> listRootDocument = documentService.findRootDocument();
            for (int i = 0; i < listRootDocument.size(); i++) {
                listRootDocument.get(i).setCreatedAt(DateUtil.formatDate(listRootDocument.get(i).getCreatedAt(),"dd/MM/yyyy"));
            }
            modelAndView.addObject("listDocument", listRootDocument);
            modelAndView.addObject("document", new Document());
            //select-option danh sách loại tài liệu
            List<LoaiTaiLieu> listLoaiTaiLieu = new ArrayList<>();
            listLoaiTaiLieu.add(loaiTaiLieuService.findDocTypeDefault());
            listLoaiTaiLieu.addAll(loaiTaiLieuService.findAllWhereStatusTrue());
            modelAndView.addObject("listLoaiTaiLieu", listLoaiTaiLieu);
            //select-option danh sách thư mục
            List<Document> listFolder = new ArrayList<>();
            listFolder.add(Document.builder().id(0).ten("--Chọn thư mục--").build());
            listFolder.addAll(documentService.findAllFolder());
            modelAndView.addObject("listFolder", listFolder);
            //Parent name
            modelAndView.addObject("documentParentName", "KHO TÀI LIỆU");
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/document/{aliasPath}")
    public ModelAndView getListDocument(@PathVariable("aliasPath") String aliasPath) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        String aliasName = FileUtil.getAliasNameFromAliasPath(aliasPath);
        int documentId = FileUtil.getIdFromAliasPath(aliasPath);
        if (documentService.findById(documentId) == null) {
            throw new NotFoundException();
        }
        Document document = documentService.findById(documentId);
        if (!(aliasName + "-" + documentId).equals(document.getAliasName() + "-" + document.getId())) {
            throw new NotFoundException();
        }
        //Kiểm tra quyền xem document
        if (!kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem() || !docShareService.isShared(documentId)) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        if (document.getLoai().equals(DocumentType.FILE.name())) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DOCUMENT_DETAIL);
            modelAndView.addObject("docDetail", document);
            modelAndView.addObject("document", new Document());
            //load metadata
            List<DocMetaResponse> docMetaResponse = documentService.getMetadata(documentId);
            modelAndView.addObject("listDocDataInfo", docMetaResponse);
            //Load file active
            modelAndView.addObject("fileActiveOfDocument", fileStorageService.findFileIsActiveOfDocument(documentId));
            //Load các version khác của document
            modelAndView.addObject("listFileOfDocument", fileStorageService.getFileOfDocument(documentId));
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            //Cây thư mục
            //modelAndView.addObject("folders", list);
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        }

        if (document.getLoai().equals(DocumentType.FOLDER.name())) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DOCUMENT);
            modelAndView.addObject("document", new Document());
            modelAndView.addObject("listDocument", documentService.findDocumentByParentId(documentId));
            //select-option danh loại tài liệu
            List<LoaiTaiLieu> listLoaiTaiLieu = new ArrayList<>();
            listLoaiTaiLieu.add(loaiTaiLieuService.findDocTypeDefault());
            listLoaiTaiLieu.addAll(loaiTaiLieuService.findAllWhereStatusTrue());
            modelAndView.addObject("listLoaiTaiLieu", listLoaiTaiLieu);
            //select-option danh sách thư mục
            List<Document> listFolder = new ArrayList<>();
            listFolder.add(documentService.findById(documentId));
            listFolder.addAll(documentService.findAllFolder());
            modelAndView.addObject("listFolder", listFolder);
            //Parent name
            modelAndView.addObject("documentParentName", document.getTen().toUpperCase());
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }

            return modelAndView;
        }

        return new ModelAndView();
    }

    //Insert FILE và FOLDER
    @PostMapping("/document/insert")
    public String insert(HttpServletRequest request,
                         @ModelAttribute("document") Document document,
                         @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (document.getTen().isEmpty()) {
            throw new NotFoundException();
        }
        document.setAliasName(FileUtil.generateAliasName(document.getTen()));
        document.setAccount(new Account(FlowieeUtil.ACCOUNT_ID));
        Document documentSaved = documentService.save(document);
        //Trường hợp document được tạo mới là file upload
        if (document.getLoai().equals(DocumentType.FILE.name()) && file != null) {
            //Lưu file đính kèm vào thư mục chứ file upload
            fileStorageService.saveFileOfDocument(file, documentSaved.getId());

            //Lưu giá trị default vào DocData
            List<DocField> listDocField = docFieldService.findByDocTypeId(new LoaiTaiLieu(document.getLoaiTaiLieu().getId()));
            for (DocField docField : listDocField) {
                DocData docData = DocData.builder()
                        .docField(DocField.builder().id(docField.getId()).build())
                        .document(Document.builder().id(document.getId()).build())
                        .noiDung("").build();
                docDataService.save(docData);
            }
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/document/change-file/{id}")
    public String changeFile(@RequestParam("file") MultipartFile file, @PathVariable("id") int id, HttpServletRequest request) throws IOException {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || file.isEmpty()) {
            throw new NotFoundException();
        }
        fileStorageService.changFileOfDocument(file, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/document/update/{id}")
    public String update(@ModelAttribute("document") Document document,
                         @PathVariable("id") int id, HttpServletRequest request) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        if (documentService.findById(id) == null) {
            throw new NotFoundException();
        }
        documentService.update(document, id);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/document/update-metadata/{id}")
    public String updateMetadata(HttpServletRequest request,
                                 @PathVariable("id") int documentId,
                                 @RequestParam("docDataId") Integer[] docDataIds,
                                 @RequestParam("docDataValue") String[] docDataValues) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (documentId <= 0) {
            throw new BadRequestException();
        }
        if (documentService.findById(documentId) == null) {
            throw new NotFoundException();
        }

        documentService.updateMetadata(docDataIds, docDataValues, documentId);

        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/document/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        Document document = documentService.findById(id);
        if (id <= 0 || document == null) {
            throw new BadRequestException();
        }
        documentService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/document/move/{id}")
    public String move() {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        return "";
    }

    @PostMapping("/document/share/{id}")
    public String share() {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        return "";
    }
}