package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.danhmuc.service.DonViTinhService;
import com.flowiee.app.danhmuc.service.LoaiKichCoService;
import com.flowiee.app.danhmuc.service.LoaiMauSacService;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.danhmuc.service.LoaiSanPhamService;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.services.*;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
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
public class SanPhamController {
    private static final Logger logger = LoggerFactory.getLogger(SanPhamController.class);

    @Autowired
    private SanPhamService productsService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ThuocTinhSanPhamService thuocTinhSanPhamService;
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
    private FileStorageService fileStorageService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModule;

    @GetMapping(value = "")
    public ModelAndView getAllProducts() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM);
            modelAndView.addObject("sanPham", new SanPham());
            modelAndView.addObject("listSanPham", productsService.findAll());
            modelAndView.addObject("listLoaiSanPham", loaiSanPhamService.findAll());
            modelAndView.addObject("listDonViTinh", donViTinhService.findAll());
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
        modelAndView.addObject("sanPham", new SanPham());
        modelAndView.addObject("bienTheSanPham", new BienTheSanPham());
        modelAndView.addObject("giaSanPham", new GiaSanPham());
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
        modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.getListVariantOfProduct(sanPhamId));
        // Danh sách đơn vị tính từ danh mục hệ thống
        modelAndView.addObject("listDonViTinh", donViTinhService.findAll());
        //List image
        modelAndView.addObject("listImageOfSanPham", fileStorageService.getImageOfSanPham(sanPhamId));
        //Image active
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPham(sanPhamId);
        modelAndView.addObject("imageActive", imageActive != null ? imageActive : new FileStorage());

        return modelAndView;
    }

    @PostMapping(value = "/insert")
    public String insertProduct(HttpServletRequest request, @ModelAttribute("sanPham") SanPham sanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        productsService.save(sanPham);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/update/{id}")
    public String updateProduct(HttpServletRequest request, @ModelAttribute("sanPham") SanPham sanPham, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || productsService.findById(id) == null) {
            throw new BadRequestException();
        }
        productsService.update(sanPham, id);


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
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_SANPHAM + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}