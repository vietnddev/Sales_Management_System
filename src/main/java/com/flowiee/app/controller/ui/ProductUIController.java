package com.flowiee.app.controller.ui;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.service.*;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(EndPointUtil.PRO_PRODUCT)
public class ProductUIController extends BaseController {
    private final ProductService productService;
    private final FileStorageService fileStorageService;
    private final VoucherService voucherService;

    @Autowired
    public ProductUIController(ProductService productService, FileStorageService fileStorageService, VoucherService voucherService) {
        this.productService = productService;
        this.fileStorageService = fileStorageService;
        this.voucherService = voucherService;
    }

    @GetMapping
    public ModelAndView loadProductPage() {
        validateModuleProduct.readProduct(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PRODUCT);
        modelAndView.addObject("templateImportName", AppConstants.TEMPLATE_I_SANPHAM);
        return baseView(modelAndView);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView viewGeneralProduct(@PathVariable("id") Integer productId) {
        try {
            validateModuleProduct.readProduct(true);
            if (productId <= 0 || productService.findProductById(productId) == null) {
                throw new NotFoundException("Product not found!");
            }
            ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PRODUCT_INFO);
            modelAndView.addObject("productId", productId);
            modelAndView.addObject("detailProducts", ProductDTO.fromProduct(productService.findProductById(productId)));
            return baseView(modelAndView);
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product detail"), ex);
            throw new AppException();
        }
    }

    @GetMapping(value = "/variant/{id}")
    public ModelAndView viewDetailProduct(@PathVariable("id") Integer variantId) {
        validateModuleProduct.readProduct(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PRODUCT_VARIANT);
        modelAndView.addObject("listThuocTinh", productService.findAllAttributes(variantId));
        modelAndView.addObject("bienTheSanPhamId", variantId);
        modelAndView.addObject("bienTheSanPham", productService.findProductVariantById(variantId));
        modelAndView.addObject("listImageOfSanPhamBienThe", fileStorageService.getImageOfSanPhamBienThe(variantId));
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPhamBienThe(variantId);
        if (imageActive == null) {
            imageActive = new FileStorage();
        }
        modelAndView.addObject("imageActive", imageActive);        
        return baseView(modelAndView);
    }

    @PostMapping("/attribute/insert")
    public ModelAndView insertProductAttribute(HttpServletRequest request, @ModelAttribute("thuocTinhSanPham") ProductAttribute productAttribute) {
        validateModuleProduct.updateProduct(true);
        productService.saveProductAttribute(productAttribute);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping(value = "/attribute/update/{id}")
    public ModelAndView updateProductAttribute(@ModelAttribute("thuocTinhSanPham") ProductAttribute attribute,
                                               @PathVariable("id") Integer attributeId,
                                               HttpServletRequest request) {
        validateModuleProduct.updateProduct(true);
        if (attributeId <= 0 || productService.findProductAttributeById(attributeId) == null) {
            throw new NotFoundException("Product attribute not found!");
        }
        attribute.setId(attributeId);
        productService.updateProductAttribute(attribute, attributeId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @DeleteMapping(value = "/attribute/delete/{id}")
    public ResponseEntity<String> deleteAttribute(@PathVariable("id") Integer attributeId) {
        validateModuleProduct.updateProduct(true);
        if (productService.findProductAttributeById(attributeId) == null) {
            throw new NotFoundException("Product attribute not found!");
        }
        return ResponseEntity.ok().body(productService.deleteProductAttribute(attributeId));
    }

    @PostMapping(value = "/variant/active-image/{sanPhamBienTheId}")
    public ModelAndView activeImageOfProductVariant(HttpServletRequest request,
                                                    @PathVariable("sanPhamBienTheId") Integer productVariantId,
                                                    @RequestParam("imageId") Integer imageId) {
        validateModuleProduct.updateImage(true);
        if (productVariantId == null || productVariantId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException("Product variant or image not found!");
        }
        fileStorageService.setImageActiveOfBienTheSanPham(productVariantId, imageId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        validateModuleProduct.readProduct(true);
        byte[] dataExport = productService.exportData(null);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + AppConstants.TEMPLATE_E_SANPHAM + ".xlsx");
        return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
    }

    @GetMapping("/voucher")
    public ModelAndView viewVouchers() {
        validateModuleProduct.readVoucher(true);
        return baseView(new ModelAndView(PagesUtils.PRO_VOUCHER));
    }

    @GetMapping("/voucher/detail/{id}")
    public ModelAndView viewVoucherDetail(@PathVariable("id") Integer voucherInfoId) {
        validateModuleProduct.readVoucher(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_VOUCHER_DETAIL);
        modelAndView.addObject("voucherInfoId", voucherInfoId);
        return baseView(modelAndView);
    }

    @PostMapping("/voucher/update/{id}")
    public ModelAndView deleteVoucher(@ModelAttribute("voucherInfo") VoucherInfo voucherInfo, @PathVariable("id") Integer voucherInfoId) {
        validateModuleProduct.updateVoucher(true);
        if (voucherInfo == null) {
            throw new BadRequestException("Voucher to update not null!");
        }
        if (voucherInfoId <= 0 || voucherService.findVoucherDetail(voucherInfoId) == null) {
            throw new NotFoundException("VoucherId invalid!");
        }
        voucherService.updateVoucher(voucherInfo ,voucherInfoId);
        return new ModelAndView("redirect:/san-pham/voucher");
    }

    @PostMapping("/voucher/delete/{id}")
    public ModelAndView deleteVoucher(@PathVariable("id") Integer voucherInfoId) {
        validateModuleProduct.deleteVoucher(true);
        voucherService.deteleVoucher(voucherInfoId);
        return new ModelAndView("redirect:/san-pham/voucher");
    }
}