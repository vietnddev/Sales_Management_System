package com.flowiee.app.controller.storage;

import com.flowiee.app.category.Category;
import com.flowiee.app.category.CategoryService;
import com.flowiee.app.common.utils.*;
import com.flowiee.app.config.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.entity.DocField;
import com.flowiee.app.service.storage.DocFieldService;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/loai-tai-lieu")
public class DocTypeController extends BaseController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService docTypeService;
    @Autowired
    private DocFieldService docFieldService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModuleDanhMuc;

    @GetMapping("")
    public ModelAndView findAllDmLoaiTaiLieu() {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.validateRead()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DANHMUC_LOAITAILIEU);
            List<Category> listLoaiTaiLieu = docTypeService.findSubCategory(CategoryUtil.DOCUMENTTYPE);
            modelAndView.addObject("listLoaiTaiLieu", listLoaiTaiLieu);
            modelAndView.addObject("loaiTaiLieu", new Category());
            modelAndView.addObject("templateImportName", FileUtil.TEMPLATE_IE_DM_LOAITAILIEU);
            modelAndView.addObject("url_template", EndPointUtil.DANHMUC_LOAITAILIEU_TEMPLATE);
            modelAndView.addObject("url_import", EndPointUtil.DANHMUC_LOAITAILIEU_IMPORT);
            modelAndView.addObject("url_export", EndPointUtil.DANHMUC_LOAITAILIEU_EXPORT);            
            if (kiemTraQuyenModuleDanhMuc.validateInsert()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.validateUpdate()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.validateDelete()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ModelAndView findDetailDocType(@PathVariable("id") int id) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.validateRead()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DOCTYPE_DETAIL);
            List<DocField> listDocField = docFieldService.findByDocTypeId(id);
            modelAndView.addObject("listDocField", listDocField);
            modelAndView.addObject("docField", new DocField());
            modelAndView.addObject("nameDocType", docTypeService.findById(id).getName().toUpperCase());
            modelAndView.addObject("docTypeId", id);            
            if (kiemTraQuyenModuleDanhMuc.validateInsert()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.validateUpdate()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModuleDanhMuc.validateDelete()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("loaiTaiLieu") Category loaiTaiLieu) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
//        if (docTypeService.findByTen(loaiTaiLieu.getTen()) != null) {
//            throw new DataExistsException();
//        }
        docTypeService.save(loaiTaiLieu);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("loaiTaiLieu") Category loaiTaiLieu,
                         @PathVariable("id") Integer docTypeId, HttpServletRequest request) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (docTypeId <= 0) {
            throw new BadRequestException();
        }
        docTypeService.update(loaiTaiLieu, docTypeId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        Category loaiTaiLieu = docTypeService.findById(id);
        if (id <= 0 || loaiTaiLieu == null) {
            throw new BadRequestException();
        }
        docTypeService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/template")
    public ResponseEntity<?> exportTemplate() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.validateImport()) {
            byte[] dataExport = docTypeService.exportTemplate();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAITAILIEU + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.validateExport()) {
            byte[] dataExport = docTypeService.exportData();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAITAILIEU + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}