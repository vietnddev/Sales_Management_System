package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.ProductAttribute;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.EximModel;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.service.ExportService;

import com.flowiee.pms.service.product.ProductAttributeService;
import com.flowiee.pms.service.product.ProductImageService;
import com.flowiee.pms.service.product.ProductInfoService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.utils.constants.CategoryType;
import com.flowiee.pms.utils.constants.Pages;
import com.flowiee.pms.utils.constants.TemplateExport;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/san-pham")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductControllerView extends BaseController {
    ProductInfoService mvProductInfoService;
    ProductImageService mvProductImageService;
    ProductVariantService mvProductVariantService;
    ProductAttributeService mvProductAttributeService;
    @Autowired
    @Qualifier("productExportServiceImpl")
    @NonFinal
    ExportService exportService;

    @GetMapping
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ModelAndView loadProductPage() {
        setupSearchTool(true, List.of(CategoryType.SIZE, CategoryType.COLOR, CategoryType.PRODUCT_TYPE));
        return baseView(new ModelAndView(Pages.PRO_PRODUCT.getTemplate()));
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ModelAndView viewGeneralProduct(@PathVariable("id") Long productId) {
        Optional<ProductDTO> product = mvProductInfoService.findById(productId);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product not found!");
        }
        ModelAndView modelAndView = new ModelAndView(Pages.PRO_PRODUCT_INFO.getTemplate());
        modelAndView.addObject("productId", productId);
        modelAndView.addObject("detailProducts", product.get());
        return baseView(modelAndView);
    }

    @GetMapping(value = "/variant/{id}")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ModelAndView viewDetailProduct(@PathVariable("id") Long variantId) {
        Optional<ProductVariantDTO> productVariant = mvProductVariantService.findById(variantId);
        if (productVariant.isEmpty()) {
            throw new BadRequestException();
        }
        ModelAndView modelAndView = new ModelAndView(Pages.PRO_PRODUCT_VARIANT.getTemplate());
        modelAndView.addObject("listAttributes", mvProductAttributeService.findAll(-1, -1, variantId).getContent());
        modelAndView.addObject("bienTheSanPhamId", variantId);
        modelAndView.addObject("bienTheSanPham", productVariant.get());
        modelAndView.addObject("listImageOfSanPhamBienThe", mvProductImageService.getImageOfProductVariant(variantId));
        FileStorage imageActive = mvProductImageService.findImageActiveOfProductVariant(variantId);
        if (imageActive == null) {
            imageActive = new FileStorage();
        }
        modelAndView.addObject("imageActive", imageActive);        
        return baseView(modelAndView);
    }

    @PostMapping("/attribute/insert")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public ModelAndView insertProductAttribute(HttpServletRequest request, @ModelAttribute("thuocTinhSanPham") ProductAttribute productAttribute) {
        mvProductAttributeService.save(productAttribute);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping(value = "/attribute/update/{id}")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public ModelAndView updateProductAttribute(@ModelAttribute("thuocTinhSanPham") ProductAttribute attribute,
                                               @PathVariable("id") Long attributeId,
                                               HttpServletRequest request) {
        if (mvProductAttributeService.findById(attributeId).isEmpty()) {
            throw new ResourceNotFoundException("Product attribute not found!");
        }
        attribute.setId(attributeId);
        mvProductAttributeService.update(attribute, attributeId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @DeleteMapping(value = "/attribute/delete/{id}")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public ResponseEntity<String> deleteAttribute(@PathVariable("id") Long attributeId) {
        return ResponseEntity.ok().body(mvProductAttributeService.delete(attributeId));
    }

    @PostMapping(value = "/variant/active-image/{sanPhamBienTheId}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView activeImageOfProductVariant(HttpServletRequest request,
                                                    @PathVariable("sanPhamBienTheId") Integer productVariantId,
                                                    @RequestParam("imageId") Integer imageId) {
        if (productVariantId == null || productVariantId <= 0 || imageId == null || imageId <= 0) {
            throw new ResourceNotFoundException("Product variant or image not found!");
        }
        mvProductImageService.setImageActiveOfProductVariant(productVariantId, imageId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/export")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public ResponseEntity<?> exportData(@RequestParam(value = "isTemplateOnly", required = false) Boolean isTemplateOnly) {
        if (isTemplateOnly == null) {
            isTemplateOnly = false;
        }
        EximModel model = null;
        if (isTemplateOnly) {
            model = exportService.exportToExcel(TemplateExport.IM_LIST_OF_PRODUCTS, null, true);
        } else {
            model = exportService.exportToExcel(TemplateExport.EX_LIST_OF_PRODUCTS, null, false);
        }
        return ResponseEntity.ok().headers(model.getHttpHeaders()).body(model.getContent());
    }
}