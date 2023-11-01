package com.flowiee.app.storage.controller;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.storage.service.FileStorageService;
import com.flowiee.app.product.services.ProductService;
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
    private ProductService productsService;
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

    @PostMapping("/file/change-image-sanpham/{id}")
    public String changeFile(@RequestParam("file") MultipartFile file,
                             @PathVariable("id") int id,
                             HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || file.isEmpty()) {
            throw new BadRequestException();
        }
        fileService.changeImageSanPham(file, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/file/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        fileService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}