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
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.model.SystemLogAction;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModuleDanhMuc;
    @Autowired
    private DocFieldService docFieldService;

    @GetMapping("")
    public ModelAndView findAllDmLoaiTaiLieu() {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DANHMUC_LOAITAILIEU);
            List<LoaiTaiLieu> listLoaiTaiLieu = loaiTaiLieuService.findAll();
            modelAndView.addObject("listLoaiTaiLieu", listLoaiTaiLieu);
            modelAndView.addObject("loaiTaiLieu", new LoaiTaiLieu());
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

    @GetMapping("/{id}")
    public ModelAndView findDetailDocType(@PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DOCTYPE_DETAIL);
            List<DocField> listDocField = docFieldService.findByDocTypeId(LoaiTaiLieu.builder().id(id).build());
            modelAndView.addObject("listDocField", listDocField);
            modelAndView.addObject("docField", new DocField());
            modelAndView.addObject("nameDocType", loaiTaiLieuService.findById(id).getTen().toUpperCase());
            modelAndView.addObject("docTypeId", id);
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

    @PostMapping("/insert")
    public String insert(@ModelAttribute("loaiTaiLieu") LoaiTaiLieu loaiTaiLieu, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (loaiTaiLieuService.findByTen(loaiTaiLieu.getTen()) != null) {
            throw new DataExistsException();
        }
        loaiTaiLieuService.save(loaiTaiLieu);
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
        return "redirect:" + request.getHeader("referer");
    }
}