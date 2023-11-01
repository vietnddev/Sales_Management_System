package com.flowiee.app.product.controller;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.category.service.*;
import com.flowiee.app.product.entity.Product;
import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.storage.entity.FileStorage;
import com.flowiee.app.storage.service.FileStorageService;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.system.service.NotificationService;
import com.flowiee.app.product.entity.Price;
import com.flowiee.app.product.services.*;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.KiemTraQuyenModuleSanPham;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/san-pham")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productsService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private LoaiMauSacService loaiMauSacService;
    @Autowired
    private LoaiKichCoService loaiKichCoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;
    @Autowired
    private DonViTinhService donViTinhService;
    @Autowired
    private FabricTypeService fabricTypeService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModule;

    @GetMapping(value = "")
    public ModelAndView getAllProducts() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM);
            modelAndView.addObject("sanPham", new Product());
            modelAndView.addObject("listSanPham", productsService.findAll());
            modelAndView.addObject("listLoaiSanPham", loaiSanPhamService.findAll());
            modelAndView.addObject("listDonViTinh", donViTinhService.findAll());
            modelAndView.addObject("templateImportName", FileUtil.TEMPLATE_I_SANPHAM);
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getDetailProduct(@PathVariable("id") int sanPhamId) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM_TONG_QUAN);
        modelAndView.addObject("sanPham", new Product());
        modelAndView.addObject("bienTheSanPham", new ProductVariant());
        modelAndView.addObject("giaSanPham", new Price());
        modelAndView.addObject("idSanPham", sanPhamId);
        // Load chi tiết thông tin sản phẩm
        modelAndView.addObject("detailProducts", productsService.findById(sanPhamId));
        // Danh sách loại sản phẩm từ danh mục hệ thống
        modelAndView.addObject("listTypeProducts", loaiSanPhamService.findAll());
        // Danh sách màu sắc từ danh mục hệ thống
        modelAndView.addObject("listDmMauSacSanPham", loaiMauSacService.findAll());
        // Danh sách kích cỡ từ danh mục hệ thống
        modelAndView.addObject("listDmKichCoSanPham", loaiKichCoService.findAll());
        // Load danh sách biến thể sản phẩm
        modelAndView.addObject("listBienTheSanPham", productVariantService.getListVariantOfProduct(sanPhamId));
        // Danh sách đơn vị tính từ danh mục hệ thống
        modelAndView.addObject("listDonViTinh", donViTinhService.findAll());
        // Danh sách chất liệu vải từ danh mục hệ thống
        modelAndView.addObject("listDmChatLieuVai", fabricTypeService.findAll());
        //List image
        modelAndView.addObject("listImageOfSanPham", fileStorageService.getImageOfSanPham(sanPhamId));
        //Image active
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPham(sanPhamId);
        modelAndView.addObject("imageActive", imageActive != null ? imageActive : new FileStorage());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
        return modelAndView;
    }

    @PostMapping(value = "/insert")
    public String insertProduct(HttpServletRequest request, @ModelAttribute("sanPham") Product product) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        productsService.save(product);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/update/{id}")
    public String updateProduct(HttpServletRequest request, @ModelAttribute("sanPham") Product product, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || productsService.findById(id) == null) {
            throw new BadRequestException();
        }
        productsService.update(product, id);


        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/delete/{id}")
    public String deleteProduct(HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (productsService.findById(id) != null) {
            productsService.delete(id);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Product not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/active-image/{sanPhamId}")
    public String activeProduct(HttpServletRequest request,
                                @PathVariable("sanPhamId") Integer sanPhamId,
                                @RequestParam("imageId") Integer imageId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (sanPhamId == null || sanPhamId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException();
        }
        fileStorageService.setImageActiveOfSanPham(sanPhamId, imageId);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
            byte[] dataExport = productsService.exportData(null);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_E_SANPHAM + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}