package com.flowiee.app.file.controller;

import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.sanpham.services.SanPhamService;
import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileStorageController {

    @Autowired
    private FileStorageService fileService;
    @Autowired
    private SanPhamService productsService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/uploads/san-pham/{id}")
    public String uploadImageOfSanPham(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                                       @PathVariable("id") int id) throws Exception {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!file.isEmpty()) {
            fileService.saveImageSanPham(file, id);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/uploads/bien-the-san-pham/{id}")
    public String uploadImageOfSanPhamBienThe(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                                              @PathVariable("id") int id) throws Exception {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!file.isEmpty()) {
            fileService.saveImageBienTheSanPham(file, id);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/file-storage/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        fileService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}