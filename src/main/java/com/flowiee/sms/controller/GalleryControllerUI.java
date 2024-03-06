package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.model.dto.FileDTO;
import com.flowiee.sms.service.FileStorageService;
import com.flowiee.sms.service.ProductService;
import com.flowiee.sms.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/gallery")
public class GalleryControllerUI extends BaseController {
    private final ProductService productService;
    private final FileStorageService fileStorageService;

    @Autowired
    public GalleryControllerUI(ProductService productService, FileStorageService fileStorageService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ModelAndView viewGallery() {
        vldModuleProduct.readGallery(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_GALLERY);
        modelAndView.addObject("listImages", FileDTO.fromFileStorages(fileStorageService.getAllImageSanPham(MODULE.PRODUCT.name())));
        modelAndView.addObject("listProducts", productService.findProductsIdAndProductName());
        return baseView(modelAndView);
    }
}