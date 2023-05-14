package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleAccount;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.danhmuc.service.LoaiSanPhamService;
import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.log.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/loai-san-pham")
public class LoaiSanPhamController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private LoaiSanPhamService loaiSanPhamService;

    @Autowired
    private SystemLogService systemLogService;

    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public String findAllSanPham(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            List<LoaiSanPham> listLoaiSP = loaiSanPhamService.findAll();
            modelMap.addAttribute("listLoaiSP", listLoaiSP);
            modelMap.addAttribute("loaiSanPham", new LoaiSanPham());
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_DANHMUC_LOAISANPHAM;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("loaiSanPham") LoaiSanPham loaiSanPham,
                         BindingResult bindingResult, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (bindingResult.hasErrors()) {
            System.out.println("Error: " + bindingResult.getFieldError());
        }
        loaiSanPhamService.save(loaiSanPham);
        systemLogService.writeLog(new SystemLog("dm_loai_san_pham", username, "Thêm mới danh mục loại sản phẩm", request.getRemoteAddr()));
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("loaiSanPham") LoaiSanPham loaiSanPham,
                         @PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        loaiSanPhamService.update(loaiSanPham, id);

        StringBuilder action = new StringBuilder("Cập nhật danh mục loại sản phẩm.");
        action.append(" Danh mục trước khi cập nhật: " + loaiSanPham.toString());
        action.append(" .Danh mục trước khi cập nhật: " + loaiSanPhamService.findById(id).toString());
        systemLogService.writeLog(new SystemLog("dm_loai_san_pham", username, action.toString(), request.getRemoteAddr()));

        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        loaiSanPhamService.delete(id);
        systemLogService.writeLog(new SystemLog("dm_loai_san_pham", username, "Xóa danh mục loại sản phẩm", request.getRemoteAddr()));

        return "redirect:" + request.getHeader("referer");
    }
}