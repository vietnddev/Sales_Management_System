package com.flowiee.app.controller.ui;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleProduct;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.FileStorageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "File API", description = "Quản lý file đính kèm và hình ảnh sản phẩm")
public class FileStorageUIController extends BaseController {
    @Autowired private FileStorageService fileService;
    @Autowired private ProductService productService;
    @Autowired private ValidateModuleProduct validateModuleProduct;

    @PostMapping("/uploads/san-pham/{id}")
    public ModelAndView uploadImageOfSanPham(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productId) throws Exception {
        validateModuleProduct.updateImage(true);
        if (productId <= 0 || productService.findProductById(productId) == null) {
            throw new NotFoundException("Product not found!");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        fileService.saveImageSanPham(file, productId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/bien-the-san-pham/{id}")
    public ModelAndView uploadImageOfSanPhamBienThe(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productVariantId) throws Exception {
        validateModuleProduct.updateImage(true);
        if (productVariantId <= 0 || productService.findProductVariantById(productVariantId) == null) {
            throw new NotFoundException("Product variant not found!");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        fileService.saveImageBienTheSanPham(file, productVariantId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/file/change-image-sanpham/{id}")
    public ModelAndView changeFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer fileId, HttpServletRequest request) {
        validateModuleProduct.updateImage(true);
        if (fileId <= 0 || fileService.findById(fileId) == null) {
            throw new NotFoundException("Image not found");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        fileService.changeImageSanPham(file, fileId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}