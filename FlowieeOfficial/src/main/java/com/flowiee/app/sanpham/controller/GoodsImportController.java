package com.flowiee.app.sanpham.controller;

import com.flowiee.app.author.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.danhmuc.service.HinhThucThanhToanService;
import com.flowiee.app.danhmuc.service.KenhBanHangService;
import com.flowiee.app.danhmuc.service.TrangThaiDonHangService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.NotificationService;
import com.flowiee.app.sanpham.entity.*;
import com.flowiee.app.sanpham.model.GoodsImportRequest;
import com.flowiee.app.sanpham.services.*;
import com.flowiee.app.storage.entity.Material;
import com.flowiee.app.storage.entity.MaterialTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/storage/goods")
public class GoodsImportController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private GoodsImportService goodsImportService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ProductVariantTempService bienTheSanPhamServiceTemp;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialTempService materialServiceTemp;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleKhoTaiLieu kiemTraQuyenModuleKho;
    //
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDonHangService donHangChiTietService;
    @Autowired
    private KenhBanHangService kenhBanHangService;
    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private TrangThaiDonHangService trangThaiDonHangService;
    @Autowired
    private DonHangThanhToanService donHangThanhToanService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemsService itemsService;

    @GetMapping("")
    public ModelAndView loadPage() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DRAFT_IMPORT);
            GoodsImport goodsImportPresent = goodsImportService.findDraftImportPresent(FlowieeUtil.ACCOUNT_ID);
            if (goodsImportPresent == null) {
                goodsImportPresent = goodsImportService.createDraftImport();
            }
            modelAndView.addObject("goodsImportRequest", new GoodsImportRequest());
            modelAndView.addObject("goodsImport", new GoodsImport());
            modelAndView.addObject("draftGoodsImport", goodsImportPresent);
            modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelAndView.addObject("listBienTheSanPhamSelected", bienTheSanPhamServiceTemp.findByImportId(goodsImportPresent.getId()));
            modelAndView.addObject("listMaterial", materialService.findAll());
            modelAndView.addObject("listMaterialSelected", materialServiceTemp.findByImportId(goodsImportPresent.getId()));
            modelAndView.addObject("listSupplier", supplierService.findAll());

            List<HinhThucThanhToan> listHinhThucThanhToan = new ArrayList<>();
            if (goodsImportPresent.getPaymentMethod() == null) {
                listHinhThucThanhToan.add(new HinhThucThanhToan(null, "Chọn hình thức thanh toán"));
                listHinhThucThanhToan.addAll(hinhThucThanhToanService.findAll());
            } else {
                listHinhThucThanhToan.add(goodsImportPresent.getPaymentMethod());
                List<HinhThucThanhToan> listAfterRemove = hinhThucThanhToanService.findAll();
                listAfterRemove.remove(goodsImportPresent.getPaymentMethod());
                listHinhThucThanhToan.addAll(listAfterRemove);
            }
            modelAndView.addObject("listHinhThucThanhToan", listHinhThucThanhToan);

            modelAndView.addObject("listTrangThaiThanhToan", FlowieeUtil.getPaymentStatusCategory());
            modelAndView.addObject("listNhanVien", accountService.findAll());
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            //
            modelAndView.addObject("listDonHang", donHangService.findAll());modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelAndView.addObject("listKenhBanHang", kenhBanHangService.findAll());
            modelAndView.addObject("listKhachHang", khachHangService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            List<Cart> listCart = cartService.findByAccountId(FlowieeUtil.ACCOUNT_ID);
            modelAndView.addObject("listCart", listCart);
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/draft/add-product/{importId}")
    public String addProductVariantToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        List<String> listProductVariantId = Arrays.stream(request.getParameterValues("productVariantId")).toList();
        for (String productVariantId : listProductVariantId) {
            BienTheSanPham bienTheSanPham = bienTheSanPhamService.findById(Integer.parseInt(productVariantId));
            bienTheSanPham.setGoodsImport(new GoodsImport(importId));
            bienTheSanPhamServiceTemp.save(ProductVariantTemp.convertFromProductVariant(bienTheSanPham));
        }
        return "redirect:/storage/goods";
    }

    @PostMapping("/draft/add-material/{importId}")
    public String addMaterialToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        List<String> listMaterialId = Arrays.stream(request.getParameterValues("materialId")).toList();
        for (String materialId : listMaterialId) {
            Material material = materialService.findById(Integer.parseInt(materialId));
            material.setGoodsImport(new GoodsImport(importId));
            materialServiceTemp.save(MaterialTemp.convertFromMaterial(material));
        }
        return "redirect:/storage/goods";
    }

    @PostMapping("/draft/save")
    public String update(@ModelAttribute("goodsImportRequest") GoodsImportRequest goodsImportRequest, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
            System.out.println("id: " + goodsImportRequest.getId());
            System.out.println("title: " + goodsImportRequest.getTitle());
            System.out.println("supplierId: " + goodsImportRequest.getSupplierId());
            System.out.println("discount: " + goodsImportRequest.getDiscount());
            System.out.println("pme: " + goodsImportRequest.getPaymentMethodId());
            System.out.println("amount: " + goodsImportRequest.getPaidAmount());
            System.out.println("pSts: " + goodsImportRequest.getPaidStatus());
            //System.out.println(goodsImportRequest.getOrderTime());
            //System.out.println(goodsImportRequest.getReceivedTime());
            System.out.println("receivedBy " + goodsImportRequest.getReceivedBy());
            System.out.println("not " + goodsImportRequest.getNote());
            System.out.println("sts " + goodsImportRequest.getStatus());
            System.out.println(" ============ ");
            goodsImportService.saveDraft(goodsImportRequest);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @ResponseBody
    @GetMapping("/search")
    public void search() {
        List<GoodsImport> data = goodsImportService.search(null, 1, null, null, null);
        if (data != null) {
            for (GoodsImport o : data) {
                System.out.println(o.toString());
            }
        }

//        if (!accountService.isLogin()) {
//            return new ModelAndView(PagesUtil.PAGE_LOGIN);
//        }
//        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
//            ModelAndView modelAndView = new ModelAndView("");
//            GoodsImport goodsImportPresent = goodsImportService.findDraftImportPresent(FlowieeUtil.ACCOUNT_ID);
//            if (goodsImportPresent == null) {
//                goodsImportPresent = goodsImportService.createDraftImport();
//            }
//            modelAndView.addObject("goodsImport", new GoodsImport());
//            modelAndView.addObject("draftGoodsImport", goodsImportPresent);
//            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
//            return modelAndView;
//        } else {
//            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
//        }
    }

    @GetMapping("/update/{id}")
    public String update(@ModelAttribute("goodsImport") GoodsImport goodsImport,
                            @PathVariable("id") Integer importId,
                            HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
            goodsImportService.update(goodsImport, importId);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/reset/{id}")
    public String clear(@PathVariable("id") Integer draftImportId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
            goodsImportService.delete(draftImportId);
            return "redirect:";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/send-approval/{id}")
    public String sendApproval(@PathVariable("id") Integer importId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
            goodsImportService.updateStatus(importId, "");
            return "redirect:";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable("id") Integer importId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKho.kiemTraQuyenTaoPhieuNhapHang()) {
            goodsImportService.updateStatus(importId, "");
            return "redirect:";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}