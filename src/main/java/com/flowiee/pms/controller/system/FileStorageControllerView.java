package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.service.product.ProductComboService;
import com.flowiee.pms.service.product.ProductImageService;
import com.flowiee.pms.service.product.ProductInfoService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.service.sales.TicketImportService;
import com.flowiee.pms.service.system.FileStorageService;

import com.flowiee.pms.utils.FileUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Tag(name = "File API", description = "Quản lý file đính kèm và hình ảnh sản phẩm")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileStorageControllerView extends BaseController {
    FileStorageService    fileService;
    TicketImportService   ticketImportService;
    TicketExportService   ticketExportService;
    ProductInfoService    productInfoService;
    ProductImageService   productImageService;
    ProductComboService   productComboService;
    ProductVariantService productVariantService;

    @PostMapping("/uploads/san-pham/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfProductBase(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productId) throws Exception {
        if (productId <= 0 || productInfoService.findById(productId).isEmpty()) {
            throw new ResourceNotFoundException("Product not found!");
        }
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File attach not found!");
        }
        productImageService.saveImageProduct(file, productId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/bien-the-san-pham/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfProductVariant(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productVariantId) throws Exception {
        if (productVariantId <= 0 || productVariantService.findById(productVariantId).isEmpty()) {
            throw new ResourceNotFoundException("Product variant not found!");
        }
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File attach not found!");
        }
        productImageService.saveImageProductVariant(file, productVariantId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/file/change-image-sanpham/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView changeFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer fileId, HttpServletRequest request) {
        if (fileId <= 0 || fileService.findById(fileId).isEmpty()) {
            throw new ResourceNotFoundException("Image not found");
        }
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File attach not found!");
        }
        productImageService.changeImageProduct(file, fileId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/ticket-import/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfTicketImport(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer ticketImportId) throws Exception {
        if (ticketImportId <= 0 || ticketImportService.findById(ticketImportId).isEmpty()) {
            throw new ResourceNotFoundException("Ticket import not found!");
        }
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File attach not found!");
        }
        productImageService.saveImageTicketImport(file, ticketImportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/ticket-export/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfTicketExport(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer ticketExportId) throws Exception {
        if (ticketExportId <= 0 || ticketExportService.findById(ticketExportId).isEmpty()) {
            throw new ResourceNotFoundException("Ticket export not found!");
        }
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File attach not found!");
        }
        productImageService.saveImageTicketExport(file, ticketExportId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/uploads/product-combo/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ModelAndView uploadImageOfProductCombo(@RequestParam("file") MultipartFile file, HttpServletRequest request, @PathVariable("id") Integer productComboId) throws Exception {
        if (productComboService.findById(productComboId).isEmpty()) {
            throw new ResourceNotFoundException("Combo not found!");
        }
        if (file.isEmpty()) {
            throw new ResourceNotFoundException("File attach doesn't empty!");
        }
        productImageService.saveImageProductCombo(file, productComboId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/uploads/**")//http://host:port/uploads/product/2024/10/3/83cbb1e4-37e9-41f1-8892-1d470ceb0f7c.jpg
    public ResponseEntity<Resource> handleFileRequest(HttpServletRequest request) throws MalformedURLException {
        isAuthenticated();
        //product/2024/10/3/83cbb1e4-37e9-41f1-8892-1d470ceb0f7c.jpg
        String pathToFile = extractPathFromPattern(request);
        //D:\Image\ uploads \ product\2024\10\3\83cbb1e4-37e9-41f1-8892-1d470ceb0f7c.jpg
        Path filePath = Paths.get(FileUtils.getFileUploadPath() + File.separator + pathToFile);
        //URL [file:/D:/Image/uploads/product/2024/10/3/83cbb1e4-37e9-41f1-8892-1d470ceb0f7c.jpg]
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String extractPathFromPattern(HttpServletRequest request) {
        // Lấy URI gốc từ request
        String fullPath = request.getRequestURI();
        // Bỏ đi phần '/uploads/' ở đầu
        return fullPath.substring("/uploads/".length());
    }
}