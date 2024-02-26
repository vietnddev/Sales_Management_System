package com.flowiee.app.controller.view;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.FileDTO;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/gallery")
public class GalleryUIController extends BaseController {
    private final ProductService productService;
    private final FileStorageService fileStorageService;

    @Autowired
    public GalleryUIController(ProductService productService, FileStorageService fileStorageService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public ModelAndView viewGallery() {
        vldModuleProduct.readGallery(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_GALLERY);
        modelAndView.addObject("listImages", FileDTO.fromFileStorages(fileStorageService.getAllImageSanPham(AppConstants.SYSTEM_MODULE.PRODUCT.name())));
        modelAndView.addObject("listProducts", productService.findProductsIdAndProductName());
        return baseView(modelAndView);
    }
}