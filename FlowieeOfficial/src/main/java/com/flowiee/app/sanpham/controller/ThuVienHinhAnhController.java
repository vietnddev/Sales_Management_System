package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/san-pham/thu-vien-hinh-anh")
public class ThuVienHinhAnhController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private SanPhamService sanPhamService;

    @GetMapping("")
    public String getAllFiles(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()){
            // Lấy tất cả ảnh cho page thư viện
            modelMap.addAttribute("listImages", fileStorageService.getAllImageSanPham(SystemModule.SAN_PHAM.name()));
            // Lấy danh sách tên sản phẩm
            modelMap.addAttribute("listSanPham", sanPhamService.getAllProducts());
            return PagesUtil.PAGE_THUVIEN;
        }
        return PagesUtil.PAGE_LOGIN;
    } }
