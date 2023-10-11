package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getAllFiles() {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username != null && !username.isEmpty()){
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_THUVIEN);
            // Lấy tất cả ảnh cho page thư viện
            modelAndView.addObject("listImages", fileStorageService.getAllImageSanPham(SystemModule.SAN_PHAM.name()));
            // Lấy danh sách tên sản phẩm
            modelAndView.addObject("listSanPham", sanPhamService.findAll());
            return modelAndView;
        }
        return new ModelAndView(PagesUtil.PAGE_LOGIN);
    }
}