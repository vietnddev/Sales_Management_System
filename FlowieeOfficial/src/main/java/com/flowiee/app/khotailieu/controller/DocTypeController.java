package com.flowiee.app.khotailieu.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.common.utils.IPUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.service.LoaiTaiLieuService;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.service.DocFieldService;
import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.log.model.SystemLogAction;
import com.flowiee.app.log.service.SystemLogService;
import com.flowiee.app.nguoidung.entity.TaiKhoan;
import com.flowiee.app.nguoidung.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/loai-tai-lieu")
public class DocTypeController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoaiTaiLieuService loaiTaiLieuService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private KiemTraQuyenModuleKhoTaiLieu kiemTraQuyenModuleKhoTaiLieu;
    @Autowired
    private DocFieldService docFieldService;

    @GetMapping("")
    public String findAllDmLoaiTaiLieu(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleReadDocType()) {
            List<LoaiTaiLieu> listLoaiTaiLieu = loaiTaiLieuService.findAll();
            modelMap.addAttribute("listLoaiTaiLieu", listLoaiTaiLieu);
            modelMap.addAttribute("loaiTaiLieu", new LoaiTaiLieu());
            if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleCreateDocType()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleUpdateDocType()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleDeleteDocType()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_DANHMUC_LOAITAILIEU;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/{id}")
    public String findDetailDocType(ModelMap modelMap, @PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleReadDocType()) {
            List<DocField> listDocField = docFieldService.findByDocTypeId(LoaiTaiLieu.builder().id(id).build());
            modelMap.addAttribute("listDocField", listDocField);
            modelMap.addAttribute("docField", new DocField());
            modelMap.addAttribute("nameDocType", loaiTaiLieuService.findById(id).getTen().toUpperCase());
            modelMap.addAttribute("docTypeId", id);
            if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleCreateDocField()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleUpdateDocField()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleDeleteDocField()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_STORAGE_DOCTYPE_DETAIL;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("loaiTaiLieu") LoaiTaiLieu loaiTaiLieu,
                         HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (loaiTaiLieuService.findByTen(loaiTaiLieu.getTen()) != null) {
            throw new DataExistsException();
        }
        loaiTaiLieuService.save(loaiTaiLieu);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module("Danh mục - Loại tài liệu")
            .action(SystemLogAction.THEM_MOI.name())
            .noiDung(loaiTaiLieu.toString())
            .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
            .ip(IPUtil.getClientIpAddress(request))
            .build();
        systemLogService.writeLog(systemLog);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("loaiTaiLieu") LoaiTaiLieu loaiTaiLieu,
                         @PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        loaiTaiLieuService.update(loaiTaiLieu, id);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module("Danh mục - Loại tài liệu")
            .action(SystemLogAction.CAP_NHAT.name())
            .noiDung(loaiTaiLieu.toString())
            .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
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
        LoaiTaiLieu loaiTaiLieu = loaiTaiLieuService.findById(id);
        if (id <= 0 || loaiTaiLieu == null) {
            throw new BadRequestException();
        }
        loaiTaiLieuService.delete(id);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module("Danh mục - Loại tài liệu")
            .action(SystemLogAction.XOA.name())
            .noiDung(loaiTaiLieu.toString())
            .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
            .ip(IPUtil.getClientIpAddress(request))
            .build();
        systemLogService.writeLog(systemLog);
        return "redirect:" + request.getHeader("referer");
    }
}