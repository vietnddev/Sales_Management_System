package com.flowiee.pms.controller.product;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.dto.FileDTO;
import com.flowiee.pms.service.product.ProductImageService;
import com.flowiee.pms.service.product.ProductInfoService;
import com.flowiee.pms.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/gallery")
public class GalleryControllerView extends BaseController {
    private final ProductInfoService productInfoService;
    private final ProductImageService productImageService;

    @Autowired
    public GalleryControllerView(ProductInfoService productInfoService, ProductImageService productImageService) {
        this.productInfoService = productInfoService;
        this.productImageService = productImageService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleProduct.readGallery(true)")
    public ModelAndView viewGallery() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_GALLERY);
        modelAndView.addObject("listImages", FileDTO.fromFileStorages(productImageService.getImageOfProduct(null)));
        modelAndView.addObject("listProducts", productInfoService.findProductsIdAndProductName());
        return baseView(modelAndView);
    }
}