package com.flowiee.app.khotailieu.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.IPUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.service.LoaiTaiLieuService;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.service.MailService;
import com.flowiee.app.khotailieu.entity.DocData;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.model.DocMetaResponse;
import com.flowiee.app.khotailieu.model.DocumentType;
import com.flowiee.app.khotailieu.repository.DocumentRepository;
import com.flowiee.app.khotailieu.service.DocDataService;
import com.flowiee.app.khotailieu.service.DocFieldService;
import com.flowiee.app.khotailieu.service.DocShareService;
import com.flowiee.app.khotailieu.service.DocumentService;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.model.SystemLogAction;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.model.module.SystemModule;
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
@RequestMapping("/kho-tai-lieu/document")
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

    //Màn hình root
    @GetMapping("")
    public ModelAndView getRootDocument() {
        String username = accountService.getUserName();
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

    @GetMapping("/{aliasPath}")
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

//    public Document buildStorageTree(List<Document> storages) {
//        Map<Integer, Document> storageMap = new HashMap<>();
//        Document root = null;
//
//        // Tạo danh sách thư mục và thêm vào map
//        for (Document storage : storages) {
//            storageMap.put(storage.getId(), storage);
//            if (storage.getParentId() == 0) {
//                root = storage; // Thư mục gốc
//            }
//        }
//
//        // Xây dựng cây thư mục
//        for (Document storage : storages) {
//            Integer parentStorageId = storage.getParentId();
//            if (parentStorageId != null) {
//                Document parentStorage = storageMap.get(parentStorageId);
//                if (parentStorage != null) {
//                    parentStorage.addSubStorage(storage);
//                }
//            }
//        }
//
//        return root;
//    }



    //Insert FILE và FOLDER
    @PostMapping("/insert")
    public String insert(HttpServletRequest request,
                         @ModelAttribute("document") Document document,
                         @RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (document.getTen().isEmpty()) {
            throw new NotFoundException();
        }
        document.setAliasName(FileUtil.generateAliasName(document.getTen()));
        document.setAccount(Account.builder().id(accountService.findIdByUsername(username)).build());
        Document documentSaved = documentService.save(document);
        //Trường hợp document được tạo mới là file upload
        if (document.getLoai().equals(DocumentType.FILE.name()) && file != null) {
            //Lưu file đính kèm vào thư mục chứ file upload
            fileStorageService.saveFileOfDocument(file, documentSaved.getId());

            //Lưu giá trị default vào DocData
            List<DocField> listDocField = docFieldService.findByDocTypeId(LoaiTaiLieu.builder().id(document.getLoaiTaiLieu().getId()).build());
            for (DocField docField : listDocField) {
                DocData docData = DocData.builder()
                        .docField(DocField.builder().id(docField.getId()).build())
                        .document(Document.builder().id(document.getId()).build())
                        .noiDung("").build();
                docDataService.save(docData);
            }
        }
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
                .module(SystemModule.KHO_TAI_LIEU.name())
                .action(SystemLogAction.THEM_MOI.name())
                .noiDung(document.toString())
                .account(Account.builder().id(accountService.findIdByUsername(username)).build())
                .ip(IPUtil.getClientIpAddress(request))
                .build();
        systemLogService.writeLog(systemLog);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/change-file/{id}")
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

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("document") Document document,
                         @PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
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
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
                .module(SystemModule.KHO_TAI_LIEU.name())
                .action(SystemLogAction.CAP_NHAT.name())
                .noiDung(document.toString())
                .account(Account.builder().id(accountService.findIdByUsername(username)).build())
                .ip(IPUtil.getClientIpAddress(request))
                .build();
        systemLogService.writeLog(systemLog);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/update-metadata/{id}")
    public String updateMetadata(HttpServletRequest request,
                                 @PathVariable("id") int documentId,
                                 @RequestParam("docDataId") Integer[] docDataIds,
                                 @RequestParam("docDataValue") String[] docDataValues) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
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

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        Document document = documentService.findById(id);
        if (id <= 0 || document == null) {
            throw new BadRequestException();
        }
        documentService.delete(id);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
                .module(SystemModule.KHO_TAI_LIEU.name())
                .action(SystemLogAction.XOA.name())
                .noiDung(document.toString())
                .account(Account.builder().id(accountService.findIdByUsername(username)).build())
                .ip(IPUtil.getClientIpAddress(request))
                .build();
        systemLogService.writeLog(systemLog);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/move/{id}")
    public String move() {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        return "";
    }

    @PostMapping("/share/{id}")
    public String share() {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        return "";
    }
}