package com.flowiee.app.sanpham.controller;

import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;
import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;
import com.flowiee.app.sanpham.model.TrangThai;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.GiaSanPhamService;
import com.flowiee.app.sanpham.services.ThuocTinhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/san-pham/variant")
public class SanPhamBienTheController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ThuocTinhSanPhamService thuocTinhSanPhamService;
    @Autowired
    private GiaSanPhamService giaSanPhamService;

    @GetMapping(value = "{id}")
    public String showDetailProduct(ModelMap modelMap, @PathVariable("id") int id) {
        /* Show trang chi tiết của biến thể
         * */
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        modelMap.addAttribute("bienTheSanPham", new BienTheSanPham());
        modelMap.addAttribute("thuocTinhSanPham", new ThuocTinhSanPham());
        modelMap.addAttribute("giaBanSanPham", new GiaSanPham());
        modelMap.addAttribute("listThuocTinh", thuocTinhSanPhamService.getAllAttributes(id));
        modelMap.addAttribute("bienTheSanPhamId", id);
        //Lấy danh sách hình ảnh của biến thể
        //modelMap.addAttribute("listFiles", fileService.getFilesByProductVariant(productVariantID));
        //
        modelMap.addAttribute("listPrices", giaSanPhamService.findByBienTheSanPhamId(id));
        return PagesUtil.PAGE_SANPHAM_BIENTHE;
    }

    @PostMapping(value = "/insert")
    public String insertVariants(HttpServletRequest request, @ModelAttribute("bienTheSanPham") BienTheSanPham bienTheSanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        bienTheSanPham.setTrangThai(TrangThai.KINH_DOANH.name());
        bienTheSanPham.setMaSanPham(DateUtil.now("yyyyMMddHHmmss"));
        bienTheSanPham.setTenBienThe(bienTheSanPham.getSanPham().getTenSanPham() + " - " + bienTheSanPham.getLoaiMauSac().getTenLoai() + " - " + bienTheSanPham.getLoaiKichCo().getTenLoai());
        bienTheSanPhamService.save(bienTheSanPham);
        //Khởi tạo giá default của giá bán
        giaSanPhamService.save(GiaSanPham.builder().bienTheSanPham(bienTheSanPham).giaBan(499D).build());
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/update/{id}")
    public String update(HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (bienTheSanPhamService.findById(id) != null) {
            //
            System.out.println("Update successfully");
        } else {
            System.out.println("Record not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String delete(HttpServletRequest request, @PathVariable("variantID") int variantID) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (bienTheSanPhamService.findById(variantID) != null) {
            bienTheSanPhamService.detele(variantID);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Record not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }
}