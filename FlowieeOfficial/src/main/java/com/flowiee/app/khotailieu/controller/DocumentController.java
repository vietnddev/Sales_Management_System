package com.flowiee.app.khotailieu.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.*;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.service.LoaiTaiLieuService;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.service.MailService;
import com.flowiee.app.khotailieu.entity.DocData;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.model.CayThuMuc;
import com.flowiee.app.khotailieu.model.DocMetaResponse;
import com.flowiee.app.khotailieu.model.DocumentType;
import com.flowiee.app.khotailieu.repository.DocumentRepository;
import com.flowiee.app.khotailieu.service.DocDataService;
import com.flowiee.app.khotailieu.service.DocFieldService;
import com.flowiee.app.khotailieu.service.DocShareService;
import com.flowiee.app.khotailieu.service.DocumentService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
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

//    private CayThuMuc chuyenDoiSangCayThuMuc(Document document) {
//        CayThuMuc cayThuMuc = new CayThuMuc();
//        cayThuMuc.setId(document.getId());
//        cayThuMuc.setTenThuMuc(document.getTen());
//        //cayThuMuc.setListSubThuMuc(new ArrayList<>());
//
//        List<Document> subDocuments = documentService.findFolderByParentId(document.getId());
//        List<CayThuMuc> subCayThuMucList = new ArrayList<>();
//
//        if (subDocuments != null) {
//            for (Document subDocument : subDocuments) {
//                CayThuMuc subCayThuMuc = chuyenDoiSangCayThuMuc(subDocument);
//                if (subCayThuMuc != null) {
//                    subCayThuMucList.add(subCayThuMuc);
//                } else {
//                    subCayThuMucList.add(new CayThuMuc());
//                }
//            }
//        }
//
//
//        cayThuMuc.setListSubThuMuc(subCayThuMucList);
//        return cayThuMuc;
//    }

//    public List<CayThuMuc> layCayThuMuc() {
//        List<Document> rootDocuments = documentService.findRootFolder();
//        List<CayThuMuc> cayThuMucList = new ArrayList<>();
//
//        for (Document rootDocument : rootDocuments) {
//            CayThuMuc cayThuMuc = chuyenDoiSangCayThuMuc(rootDocument);
//            if (cayThuMuc != null) {
//                cayThuMucList.add(cayThuMuc);
//            } else {
//                cayThuMucList.add(new CayThuMuc());
//            }
//        }
//        return cayThuMucList;
//    }

    public List<CayThuMuc> buildTree() {
        List<Document> documents = documentService.findAllFolder();

        // Tạo một map để lưu các cấu trúc cây thư mục dưới dạng key-value
        Map<Integer, CayThuMuc> map = new HashMap<>();

        // Tạo danh sách gốc cho cây thư mục
        List<CayThuMuc> roots = new ArrayList<>();

        // Đầu tiên, hãy tạo tất cả các nút (CayThuMuc) từ danh sách tài liệu (documents)
        for (Document document : documents) {
            CayThuMuc cayThuMuc = new CayThuMuc();
            cayThuMuc.setId(document.getId());
            cayThuMuc.setTenThuMuc(document.getTen());
            cayThuMuc.setListSubThuMuc(new ArrayList<>()); // Khởi tạo danh sách con trống ban đầu
            map.put(document.getId(), cayThuMuc);

            // Nếu là thư mục gốc (parentId == 0), thêm vào danh sách gốc
            if (document.getParentId() == 0) {
                roots.add(cayThuMuc);
            }
        }

        // Sau đó, lặp qua danh sách tài liệu lần nữa để xây dựng cây thư mục
        for (Document document : documents) {
            int parentId = document.getParentId();

            // Bỏ qua các thư mục gốc vì chúng đã được thêm vào danh sách gốc
            if (parentId == 0) {
                continue;
            }

            // Lấy CayThuMuc đại diện cho thư mục cha
            CayThuMuc parentCayThuMuc = map.get(parentId);

            // Nếu thư mục cha không tồn tại (có thể do dữ liệu không hợp lệ), thì bỏ qua thư mục này
            if (parentCayThuMuc == null) {
                continue;
            }

            // Lấy danh sách con của thư mục cha và thêm CayThuMuc hiện tại vào danh sách đó
            parentCayThuMuc.getListSubThuMuc().add(map.get(document.getId()));
        }

        return roots;
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
        ///
//        List<CayThuMuc> cayThuMuc = new ArrayList<>();
//
//        List<Document> listRoot = documentService.findRootDocument();
//        for (Document docRoot : listRoot) {
//            CayThuMuc rootFolder = new CayThuMuc();
//            rootFolder.setId(docRoot.getId());
//            rootFolder.setTenThuMuc(docRoot.getTen());
//
//            List<CayThuMuc> listSub = new ArrayList<>();
//            List<Document> listSubDoc = documentService.findDocumentByParentId(docRoot.getId());
//            if (listSubDoc != null) {
//                for (Document doc : listSubDoc) {
//                    CayThuMuc subFolder = new CayThuMuc();
//                    subFolder.setId(doc.getId());
//                    subFolder.setTenThuMuc(doc.getTen());
//                    listSub.add(subFolder);
//                }
//            }
//
//            rootFolder.setListSubThuMuc(listSub);
//            cayThuMuc.add(rootFolder);
//
//
//        }
//
//        System.out.println(cayThuMuc);

        //List<CayThuMuc> listCTM = layCayThuMuc();
//
//        List<CayThuMuc> cayThuMucList = cayThuMucService.layCayThuMuc();
//        model.addAttribute("folders", cayThuMucList);

         List<CayThuMuc> list = buildTree();
        System.out.println(list);

        ///
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
            List<DocField> listDocField = docFieldService.findByDocTypeId(LoaiTaiLieu.builder().id(document.getLoaiTaiLieu().getId()).build());
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