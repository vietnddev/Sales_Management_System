package com.flowiee.app.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/san-pham/thu-vien-hinh-anh")
public class GalleryController extends BaseController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public ModelAndView getAllFiles() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_GALLERY);
        // Lấy tất cả ảnh cho page thư viện
        modelAndView.addObject("listImages", fileStorageService.getAllImageSanPham(SystemModule.SAN_PHAM.name()));
        // Lấy danh sách tên sản phẩm
        modelAndView.addObject("listSanPham", productService.findAll());
        return baseView(modelAndView);
    }
}