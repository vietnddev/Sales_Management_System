package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.danhmuc.service.LoaiSanPhamService;
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
@RequestMapping("/danh-muc/loai-san-pham")
public class LoaiSanPhamController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public ModelAndView findAllSanPham() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DANHMUC_LOAISANPHAM);
            List<LoaiSanPham> listLoaiSP = loaiSanPhamService.findAll();
            modelAndView.addObject("listLoaiSP", listLoaiSP);
            modelAndView.addObject("loaiSanPham", new LoaiSanPham());
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("loaiSanPham") LoaiSanPham loaiSanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        loaiSanPhamService.save(loaiSanPham);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("loaiSanPham") LoaiSanPham loaiSanPham,
                         @PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        loaiSanPhamService.update(loaiSanPham, id);
         return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        LoaiSanPham loaiSanPham = loaiSanPhamService.findById(id);
        if (id <= 0 || loaiSanPham == null) {
            throw new BadRequestException();
        }
        loaiSanPhamService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
            byte[] dataExport = loaiSanPhamService.exportData();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_DM_LOAISANPHAM + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}