package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.product.ProductImageService;
import com.flowiee.pms.service.product.ProductInfoService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.service.sales.TicketImportService;
import com.flowiee.pms.service.system.FileStorageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "File API", description = "Quản lý file đính kèm và hình ảnh sản phẩm")
public class FileStorageControllerView extends BaseController {
    @Autowired
    private FileStorageService fileService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private TicketImportService ticketImportService;
    @Autowired
    private TicketExportService ticketExportService;

    @PostMapping("/uploads/san-pham/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfSanPham(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productId) throws Exception {
        if (productId <= 0 || productInfoService.findById(productId) == null) {
            throw new NotFoundException("Product not found!");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        productImageService.saveImageProduct(file, productId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/bien-the-san-pham/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfSanPhamBienThe(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productVariantId) throws Exception {
        if (productVariantId <= 0 || productVariantService.findById(productVariantId).isEmpty()) {
            throw new NotFoundException("Product variant not found!");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        productImageService.saveImageProductVariant(file, productVariantId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/file/change-image-sanpham/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView changeFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer fileId, HttpServletRequest request) {
        if (fileId <= 0 || fileService.findById(fileId).isEmpty()) {
            throw new NotFoundException("Image not found");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        productImageService.changeImageProduct(file, fileId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/ticket-import/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfTicketImport(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer ticketImportId) throws Exception {
        if (ticketImportId <= 0 || ticketImportService.findById(ticketImportId).isEmpty()) {
            throw new NotFoundException("Ticket import not found!");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        productImageService.saveImageTicketImport(file, ticketImportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/ticket-export/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfTicketExport(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer ticketExportId) throws Exception {
        if (ticketExportId <= 0 || ticketExportService.findById(ticketExportId).isEmpty()) {
            throw new NotFoundException("Ticket export not found!");
        }
        if (file.isEmpty()) {
            throw new NotFoundException("File attach not found!");
        }
        productImageService.saveImageTicketExport(file, ticketExportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}