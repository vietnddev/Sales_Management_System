package com.flowiee.app.product.controller;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.storage.entity.FileStorage;
import com.flowiee.app.storage.service.FileStorageService;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.system.service.NotificationService;
import com.flowiee.app.product.entity.Price;
import com.flowiee.app.product.entity.ProductAttribute;
import com.flowiee.app.product.model.TrangThai;
import com.flowiee.app.product.services.ProductVariantService;
import com.flowiee.app.product.services.PriceService;
import com.flowiee.app.product.services.ProductAttributeService;
import com.flowiee.app.config.KiemTraQuyenModuleSanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/san-pham/variant")
public class ProductVariantController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping(value = "{id}")
    public ModelAndView showDetailProduct(@PathVariable("id") int bienTheSanPhamId) {
        // Show trang chi tiết của biến thể
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM_BIENTHE);
        modelAndView.addObject("bienTheSanPham", new ProductVariant());
        modelAndView.addObject("thuocTinhSanPham", new ProductAttribute());
        modelAndView.addObject("giaBanSanPham", new Price());
        modelAndView.addObject("listThuocTinh", productAttributeService.getAllAttributes(bienTheSanPhamId));
        modelAndView.addObject("bienTheSanPhamId", bienTheSanPhamId);
        modelAndView.addObject("bienTheSanPham", productVariantService.findById(bienTheSanPhamId));
        modelAndView.addObject("listImageOfSanPhamBienThe", fileStorageService.getImageOfSanPhamBienThe(bienTheSanPhamId));
        modelAndView.addObject("listPrices", priceService.findByBienTheSanPhamId(bienTheSanPhamId));
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPhamBienThe(bienTheSanPhamId);
        if (imageActive == null) {
            imageActive = new FileStorage();
        }
        modelAndView.addObject("imageActive", imageActive);
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
        return modelAndView;
    }

    @PostMapping(value = "/insert")
    public String insertVariants(HttpServletRequest request, @ModelAttribute("bienTheSanPham") ProductVariant productVariant) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        productVariant.setTrangThai(TrangThai.KINH_DOANH.name());
        productVariant.setMaSanPham(DateUtil.now("yyyyMMddHHmmss"));
        productVariantService.save(productVariant);
        //Khởi tạo giá default của giá bán
        priceService.save(Price.builder().productVariant(productVariant).giaBan(0D).trangThai(true).build());
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/update/{id}")
    public String update(HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (productVariantService.findById(id) != null) {
            //
            System.out.println("Update successfully");
        } else {
            System.out.println("Record not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable("variantID") Integer variantID) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (productVariantService.findById(variantID) != null) {
            productVariantService.delete(variantID);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Record not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/gia-ban/update/{id}")
    public String updateGiaBan(HttpServletRequest request,
                               @ModelAttribute("price") Price price,
                               @PathVariable("id") int idBienTheSanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenQuanLyGiaBan()) {
            int idGiaBanHienTai = Integer.parseInt(request.getParameter("idGiaBan"));
            priceService.update(price, idBienTheSanPham, idGiaBanHienTai);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/active-image/{sanPhamBienTheId}")
    public String activeProduct(HttpServletRequest request,
                                @PathVariable("sanPhamBienTheId") Integer sanPhamBienTheId,
                                @RequestParam("imageId") Integer imageId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (sanPhamBienTheId == null || sanPhamBienTheId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException();
        }
        fileStorageService.setImageActiveOfBienTheSanPham(sanPhamBienTheId, imageId);
        return "redirect:" + request.getHeader("referer");
    }
}