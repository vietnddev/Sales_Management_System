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
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.khotailieu.entity.DocData;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.model.DocumentType;
import com.flowiee.app.khotailieu.service.DocDataService;
import com.flowiee.app.khotailieu.service.DocFieldService;
import com.flowiee.app.khotailieu.service.DocShareService;
import com.flowiee.app.khotailieu.service.DocumentService;
import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.log.model.SystemLogAction;
import com.flowiee.app.log.service.SystemLogService;
import com.flowiee.app.account.entity.Account;
import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    //Màn hình root
    @GetMapping("")
    public String getRootDocument(ModelMap modelMap, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            List<Document> listRootDocument = documentService.findRootDocument();
            modelMap.addAttribute("listDocument", listRootDocument);
            modelMap.addAttribute("document", new Document());
            modelMap.addAttribute("listLoaiTaiLieu", loaiTaiLieuService.findAllWhereStatusTrue());
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenThemMoi()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_STORAGE_DOCUMENT;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/{aliasPath}")
    public String getListDocument(@PathVariable("aliasPath") String aliasPath, ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
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
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem() && docShareService.isShared(documentId)) {
            modelMap.addAttribute("document", document);
            modelMap.addAttribute("document", new Document());
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            //Nếu document truy cập đến là file thì ném qua page view chi tiết
            // Ngược lại nếu là FOLDER thì ném qua danh sách document
            if (document.getLoai().equals(DocumentType.FILE.name())) {
                List<DocData> listDocData = document.getListDocData();
                LinkedHashMap<String, String> listDocDataInfo = new LinkedHashMap<>();
                //Đưa thông tin tên field và nội dung field vào ls
                for (DocData docData :listDocData) {
                    listDocDataInfo.put(docData.getDocField().getTenField(), docData.getNoiDung());
                }
                modelMap.addAttribute("listDocDataInfo", listDocDataInfo);
                //Trả về page xem thông tin chi tiết file
                return PagesUtil.PAGE_STORAGE_DOCUMENT_DETAIL;
            } else {
                return PagesUtil.PAGE_STORAGE_DOCUMENT;
            }
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    //Insert FILE và FOLDER
    @PostMapping("/insert")
    public String insert(@ModelAttribute("document") Document document, HttpServletRequest request,
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
        //Nếu document là FILE -> Lưu giá trị default vào DocData
        if (document.getLoai().equals(DocumentType.FILE.name()) && file != null) {
            Path path = Paths.get("src\\main\\resources\\static\\uploads\\kho-tai-lieu\\2023\\05\\22\\" + DateUtil.now("yyyy.MM.dd.HH.mm.ss") + "_" + file.getOriginalFilename());
            file.transferTo(path);
            FileStorage fileStorage = FileStorage.builder()
                                                 .module(SystemModule.KHO_TAI_LIEU.name())
                                                 .tenFileGoc(file.getOriginalFilename())
                                                 .extension(FileUtil.getExtension(file.getOriginalFilename()))
                                                 .kichThuocFile(file.getSize())
                                                 .tenFileKhiLuu(DateUtil.now("yyyy.MM.dd.HH.mm.ss") + "_" + file.getOriginalFilename())
                                                 .contentType(file.getContentType())
                                                 .document(Document.builder().id(documentSaved.getId()).build())
                                                 .account(Account.builder().id(accountService.findIdByUsername(username)).build()).build();
            //fileStorageService.save(file, fileStorage);
            List<DocField> listDocField = docFieldService.findByDocTypeId(LoaiTaiLieu.builder().id(document.getLoaiTaiLieu().getId()).build());
            for (DocField docField: listDocField) {
                DocData docData = DocData.builder()
                                         .docField(DocField.builder().id(docField.getId()).build())
                                         .document(Document.builder().id(document.getId()).build())
                                         .noiDung(null).build();
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
        return "redirect:";
    }

    @PostMapping("/change-file/{id}")
    public String changeFile(@RequestParam("file") MultipartFile file, @PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || documentService.findById(id) == null || file.isEmpty()) {
            throw new BadRequestException();
        }
        //String fileNameToSave = DateUtil.now("yyyy.MM.dd.HH.mm.ss") + FileUtil.getExtension();
        FileStorage fileStorage = FileStorage.builder()
            .module(SystemModule.KHO_TAI_LIEU.name())
            .contentType(file.getContentType())
            .extension(FileUtil.getExtension(file.getOriginalFilename()))
            .kichThuocFile(file.getSize())
            .tenFileGoc(file.getOriginalFilename())
            //.tenFileKhiLuu()

            .build();
        //fileStorageService.save(fileStorage);

//        documentService.update(document);
//        //Ghi log
//        SystemLog systemLog = SystemLog.builder()
//            .module("Kho tài liệu")
//            .action(SystemLogAction.CAP_NHAT.name())
//            .noiDung(document.toString())
//            .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
//            .ip(IPUtil.getClientIpAddress(request))
        //.build();
        //systemLogService.writeLog(systemLog);
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
        documentService.update(document);
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