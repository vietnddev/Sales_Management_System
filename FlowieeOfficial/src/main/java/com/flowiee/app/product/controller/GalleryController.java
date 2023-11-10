package com.flowiee.app.product.controller;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.storage.service.FileStorageService;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.product.services.ProductService;
import com.flowiee.app.service.system.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/san-pham/thu-vien-hinh-anh")
public class GalleryController extends BaseController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ModelAndView getAllFiles() {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username != null && !username.isEmpty()){
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_THUVIEN);
            // Lấy tất cả ảnh cho page thư viện
            modelAndView.addObject("listImages", fileStorageService.getAllImageSanPham(SystemModule.SAN_PHAM.name()));
            // Lấy danh sách tên sản phẩm
            modelAndView.addObject("listSanPham", productService.findAll());            
            return baseView(modelAndView);
        }
        return new ModelAndView(PagesUtil.PAGE_LOGIN);
    }
}