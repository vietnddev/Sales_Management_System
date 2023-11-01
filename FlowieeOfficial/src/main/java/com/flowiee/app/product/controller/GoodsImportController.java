package com.flowiee.app.product.controller;

import com.flowiee.app.config.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.category.entity.HinhThucThanhToan;
import com.flowiee.app.category.service.HinhThucThanhToanService;
import com.flowiee.app.system.entity.Account;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.system.service.NotificationService;
import com.flowiee.app.product.entity.*;
import com.flowiee.app.product.model.GoodsImportRequest;
import com.flowiee.app.product.services.*;
import com.flowiee.app.storage.entity.Material;
import com.flowiee.app.storage.entity.MaterialTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/storage/goods")
public class GoodsImportController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private GoodsImportService goodsImportService;
    @Autowired
    private SupplierService productService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductVariantTempService bienTheSanPhamServiceTemp;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialTempService materialServiceTemp;
    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleKhoTaiLieu kiemTraQuyenModuleKho;

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
            modelAndView.addObject("orderTime", goodsImportPresent.getOrderTime().toString().substring(0, 10));
            modelAndView.addObject("receivedTime", goodsImportPresent.getReceivedTime().toString().substring(0, 10));
            modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
            modelAndView.addObject("listBienTheSanPhamSelected", bienTheSanPhamServiceTemp.findByImportId(goodsImportPresent.getId()));
            modelAndView.addObject("listMaterial", materialService.findAll());
            modelAndView.addObject("listMaterialSelected", materialServiceTemp.findByImportId(goodsImportPresent.getId()));

            List<Supplier> listSupplier = new ArrayList<>();
            if (goodsImportPresent.getSupplier() == null) {
                listSupplier.add(new Supplier(null, "Chọn supplier"));
                listSupplier.addAll(productService.findAll());
            } else {
                listSupplier.add(goodsImportPresent.getSupplier());
                List<Supplier> listSupplierTemp = productService.findAll();
                listSupplierTemp.remove(goodsImportPresent.getSupplier());
                listSupplier.addAll(listSupplierTemp);
            }
            modelAndView.addObject("listSupplier", listSupplier);

            List<HinhThucThanhToan> listHinhThucThanhToan = new ArrayList<>();
            if (goodsImportPresent.getPaymentMethod() == null) {
                listHinhThucThanhToan.add(new HinhThucThanhToan(null, "Chọn hình thức thanh toán"));
                listHinhThucThanhToan.addAll(hinhThucThanhToanService.findAll());
            } else {
                listHinhThucThanhToan.add(goodsImportPresent.getPaymentMethod());
                List<HinhThucThanhToan> listHinhThucThanhToanTemp = hinhThucThanhToanService.findAll();
                listHinhThucThanhToanTemp.remove(goodsImportPresent.getPaymentMethod());
                listHinhThucThanhToan.addAll(listHinhThucThanhToanTemp);
            }
            modelAndView.addObject("listHinhThucThanhToan", listHinhThucThanhToan);

            List<Account> listAccount = new ArrayList<>();
            if (goodsImportPresent.getReceivedBy() == null) {
                listAccount.add(new Account(null, null, "Chọn người nhập hàng"));
                listAccount.addAll(accountService.findAll());
            } else {
                listAccount.add(goodsImportPresent.getReceivedBy());
                List<Account> lístAccountTemp = accountService.findAll();
                lístAccountTemp.remove(goodsImportPresent.getReceivedBy());
                listAccount.addAll(lístAccountTemp);
            }
            modelAndView.addObject("listNhanVien", listAccount);

            Map<String, String> listTrangThaiThanhToan = new HashMap<>();
            if (goodsImportPresent.getPaidStatus() == null || goodsImportPresent.getPaidStatus().isEmpty()) {
                listTrangThaiThanhToan.put(null, "Chọn trạng thái thanh toán");
                listTrangThaiThanhToan.putAll(FlowieeUtil.getPaymentStatusCategory());
            } else {
                listTrangThaiThanhToan.put(goodsImportPresent.getPaidStatus(), FlowieeUtil.getPaymentStatusCategory().get(goodsImportPresent.getPaidStatus()));
                Map<String, String> listTrangThaiThanhToanTemp = FlowieeUtil.getPaymentStatusCategory();
                listTrangThaiThanhToanTemp.remove(goodsImportPresent.getPaidStatus());
                listTrangThaiThanhToan.putAll(listTrangThaiThanhToanTemp);
            }
            modelAndView.addObject("listTrangThaiThanhToan", listTrangThaiThanhToan);

            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            //
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
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
            ProductVariant productVariant = productVariantService.findById(Integer.parseInt(productVariantId));
            productVariant.setGoodsImport(new GoodsImport(importId));
            bienTheSanPhamServiceTemp.save(ProductVariantTemp.convertFromProductVariant(productVariant));
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
            goodsImportRequest.setOrderTime(DateUtil.convertStringToDate(request.getParameter("orderTime_"), "yyyy-MM-dd"));
            goodsImportRequest.setReceivedTime(DateUtil.convertStringToDate(request.getParameter("receivedTime_"), "yyyy-MM-dd"));
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