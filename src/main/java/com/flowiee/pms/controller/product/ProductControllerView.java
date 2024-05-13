package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.ProductAttribute;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.service.product.*;
import com.flowiee.pms.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/san-pham")
public class ProductControllerView extends BaseController {
    private final ProductInfoService productInfoService;
    private final ProductVariantService productVariantService;
    private final ProductAttributeService productAttributeService;
    private final ProductExportService productExportService;
    private final ProductImageService productImageService;

    @Autowired
    public ProductControllerView(ProductInfoService productInfoService, ProductVariantService productVariantService,
                                 ProductAttributeService productAttributeService, ProductExportService productExportService,
                                 ProductImageService productImageService) {
        this.productInfoService = productInfoService;
        this.productVariantService = productVariantService;
        this.productAttributeService = productAttributeService;
        this.productExportService = productExportService;
        this.productImageService = productImageService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ModelAndView loadProductPage() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PRODUCT);
        modelAndView.addObject("templateImportName", AppConstants.TEMPLATE_I_SANPHAM);
        return baseView(modelAndView);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ModelAndView viewGeneralProduct(@PathVariable("id") Integer productId) {
        Optional<ProductDTO> product = productInfoService.findById(productId);
        if (product.isEmpty()) {
            throw new NotFoundException("Product not found!");
        }
        try {
            ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PRODUCT_INFO);
            modelAndView.addObject("productId", productId);
            modelAndView.addObject("detailProducts", product.get());
            return baseView(modelAndView);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product variant"), ex);
        }
    }

    @GetMapping(value = "/variant/{id}")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ModelAndView viewDetailProduct(@PathVariable("id") Integer variantId) {
        Optional<ProductVariantDTO> productVariant = productVariantService.findById(variantId);
        if (productVariant.isEmpty()) {
            throw new BadRequestException();
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PRODUCT_VARIANT);
        modelAndView.addObject("listAttributes", productAttributeService.findAll(-1, -1, variantId).getContent());
        modelAndView.addObject("bienTheSanPhamId", variantId);
        modelAndView.addObject("bienTheSanPham", productVariant.get());
        modelAndView.addObject("listImageOfSanPhamBienThe", productImageService.getImageOfProductVariant(variantId));
        FileStorage imageActive = productImageService.findImageActiveOfProductVariant(variantId);
        if (imageActive == null) {
            imageActive = new FileStorage();
        }
        modelAndView.addObject("imageActive", imageActive);        
        return baseView(modelAndView);
    }

    @PostMapping("/attribute/insert")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public ModelAndView insertProductAttribute(HttpServletRequest request, @ModelAttribute("thuocTinhSanPham") ProductAttribute productAttribute) {
        productAttributeService.save(productAttribute);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping(value = "/attribute/update/{id}")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public ModelAndView updateProductAttribute(@ModelAttribute("thuocTinhSanPham") ProductAttribute attribute,
                                               @PathVariable("id") Integer attributeId,
                                               HttpServletRequest request) {
        if (productAttributeService.findById(attributeId).isEmpty()) {
            throw new NotFoundException("Product attribute not found!");
        }
        attribute.setId(attributeId);
        productAttributeService.update(attribute, attributeId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @DeleteMapping(value = "/attribute/delete/{id}")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public ResponseEntity<String> deleteAttribute(@PathVariable("id") Integer attributeId) {
        return ResponseEntity.ok().body(productAttributeService.delete(attributeId));
    }

    @PostMapping(value = "/variant/active-image/{sanPhamBienTheId}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView activeImageOfProductVariant(HttpServletRequest request,
                                                    @PathVariable("sanPhamBienTheId") Integer productVariantId,
                                                    @RequestParam("imageId") Integer imageId) {
        if (productVariantId == null || productVariantId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException("Product variant or image not found!");
        }
        productImageService.setImageActiveOfProductVariant(productVariantId, imageId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/export")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ResponseEntity<?> exportData() {
        return productExportService.exportToExcel(null, null, true);
    }
}