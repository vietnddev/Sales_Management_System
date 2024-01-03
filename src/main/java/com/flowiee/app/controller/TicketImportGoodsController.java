package com.flowiee.app.controller;

import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.TicketImportGoods;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.MaterialTemp;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.entity.ProductVariantTemp;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.request.TicketImportGoodsRequest;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Category;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.service.TicketImportGoodsService;
import com.flowiee.app.service.MaterialService;
import com.flowiee.app.service.MaterialTempService;
import com.flowiee.app.service.ProductVariantService;
import com.flowiee.app.service.ProductVariantTempService;
import com.flowiee.app.service.SupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(EndPointUtil.STORAGE_TICKET_IMPORT)
public class TicketImportGoodsController extends BaseController {
    @Autowired private TicketImportGoodsService ticketImportGoodsService;
    @Autowired private SupplierService productService;
    @Autowired private ProductVariantService productVariantService;
    @Autowired private ProductVariantTempService productVariantServiceTemp;
    @Autowired private MaterialService materialService;
    @Autowired private MaterialTempService materialServiceTemp;
    @Autowired private CategoryService categoryService;
    @Autowired private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView loadPage() {
        validateModuleStorage.importGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.STG_TICKET_IMPORT);
        TicketImportGoods ticketImportGoodsPresent = ticketImportGoodsService.findDraftImportPresent(CommonUtil.getCurrentAccountId());
        if (ticketImportGoodsPresent == null) {
            ticketImportGoodsPresent = ticketImportGoodsService.createDraftImport();
        }
        modelAndView.addObject("goodsImportRequest", new TicketImportGoodsRequest());
        modelAndView.addObject("goodsImport", new TicketImportGoods());
        modelAndView.addObject("draftGoodsImport", ticketImportGoodsPresent);
        modelAndView.addObject("orderTime", ticketImportGoodsPresent.getOrderTime().toString().substring(0, 10));
        modelAndView.addObject("receivedTime", ticketImportGoodsPresent.getReceivedTime().toString().substring(0, 10));
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
        modelAndView.addObject("listBienTheSanPhamSelected", productVariantServiceTemp.findByImportId(ticketImportGoodsPresent.getId()));
        modelAndView.addObject("listMaterial", materialService.findAll());
        modelAndView.addObject("listMaterialSelected", materialServiceTemp.findByImportId(ticketImportGoodsPresent.getId()));

        List<Supplier> listSupplier = new ArrayList<>();
        if (ticketImportGoodsPresent.getSupplier() == null) {
            listSupplier.add(new Supplier(null, "Chọn supplier"));
            listSupplier.addAll(productService.findAll());
        } else {
            listSupplier.add(ticketImportGoodsPresent.getSupplier());
            List<Supplier> listSupplierTemp = productService.findAll();
            listSupplierTemp.remove(ticketImportGoodsPresent.getSupplier());
            listSupplier.addAll(listSupplierTemp);
        }
        modelAndView.addObject("listSupplier", listSupplier);

        List<Category> listHinhThucThanhToan = new ArrayList<>();
        if (ticketImportGoodsPresent.getPaymentMethod() == null) {
            listHinhThucThanhToan.add(new Category(null, "Chọn hình thức thanh toán"));
            listHinhThucThanhToan.addAll(categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));
        } else {
            listHinhThucThanhToan.add(ticketImportGoodsPresent.getPaymentMethod());
            List<Category> listHinhThucThanhToanTemp = categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName());
            listHinhThucThanhToanTemp.remove(ticketImportGoodsPresent.getPaymentMethod());
            listHinhThucThanhToan.addAll(listHinhThucThanhToanTemp);
        }
        modelAndView.addObject("listHinhThucThanhToan", listHinhThucThanhToan);

        List<Account> listAccount = new ArrayList<>();
        if (ticketImportGoodsPresent.getReceivedBy() == null) {
            listAccount.add(new Account(null, null, "Chọn người nhập hàng"));
            listAccount.addAll(accountService.findAll());
        } else {
            listAccount.add(ticketImportGoodsPresent.getReceivedBy());
            List<Account> lístAccountTemp = accountService.findAll();
            lístAccountTemp.remove(ticketImportGoodsPresent.getReceivedBy());
            listAccount.addAll(lístAccountTemp);
        }
        modelAndView.addObject("listNhanVien", listAccount);

        Map<String, String> listTrangThaiThanhToan = new HashMap<>();
        if (ticketImportGoodsPresent.getPaidStatus() == null || ticketImportGoodsPresent.getPaidStatus().isEmpty()) {
            listTrangThaiThanhToan.put(null, "Chọn trạng thái thanh toán");
            listTrangThaiThanhToan.putAll(CommonUtil.getPaymentStatusCategory());
        } else {
            listTrangThaiThanhToan.put(ticketImportGoodsPresent.getPaidStatus(), CommonUtil.getPaymentStatusCategory().get(ticketImportGoodsPresent.getPaidStatus()));
            Map<String, String> listTrangThaiThanhToanTemp = CommonUtil.getPaymentStatusCategory();
            listTrangThaiThanhToanTemp.remove(ticketImportGoodsPresent.getPaidStatus());
            listTrangThaiThanhToan.putAll(listTrangThaiThanhToanTemp);
        }
        modelAndView.addObject("listTrangThaiThanhToan", listTrangThaiThanhToan);

        //
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());

        return baseView(modelAndView);
    }

    @PostMapping("/draft/add-product/{importId}")
    public ModelAndView addProductVariantToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportGoodsService.findById(importId) == null) {
            throw new NotFoundException("Goods import to add product not found!");
        }
        List<String> listProductVariantId = Arrays.stream(request.getParameterValues("productVariantId")).toList();
        for (String productVariantId : listProductVariantId) {
            ProductVariant productVariant = productVariantService.findById(Integer.parseInt(productVariantId));
            productVariant.setTicketImportGoods(new TicketImportGoods(importId));

            ProductVariantTemp temp = productVariantServiceTemp.findProductVariantInGoodsImport(importId, productVariant.getId());
            if (temp != null) {
                productVariantServiceTemp.updateSoLuong(temp.getSoLuongKho() + 1, temp.getId());
            } else {
                productVariantServiceTemp.save(ProductVariantTemp.convertFromProductVariant(productVariant));
            }
        }
        return new ModelAndView("redirect:/storage/goods");
    }

    @PostMapping("/draft/add-material/{importId}")
    public String addMaterialToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportGoodsService.findById(importId) == null) {
            throw new NotFoundException("Goods import to add material not found!");
        }
        List<String> listMaterialId = Arrays.stream(request.getParameterValues("materialId")).toList();
        for (String materialId : listMaterialId) {
            Material material = materialService.findById(Integer.parseInt(materialId));
            material.setTicketImportGoods(new TicketImportGoods(importId));

            MaterialTemp temp = materialServiceTemp.findMaterialInGoodsImport(importId, material.getId());
            if (temp != null) {
                temp.setQuantity(temp.getQuantity() + 1);
                materialServiceTemp.update(temp, temp.getId());
            } else {
                materialServiceTemp.save(MaterialTemp.convertFromMaterial(material));
            }
        }
        return "redirect:/storage/goods";
    }

    @PostMapping("/draft/save")
    public ModelAndView update(@ModelAttribute("goodsImportRequest") TicketImportGoodsRequest ticketImportGoodsRequest, HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        ticketImportGoodsRequest.setOrderTime(CommonUtil.convertStringToDate(request.getParameter("orderTime_"), "yyyy-MM-dd"));
        ticketImportGoodsRequest.setReceivedTime(CommonUtil.convertStringToDate(request.getParameter("receivedTime_"), "yyyy-MM-dd"));
        ticketImportGoodsService.saveDraft(ticketImportGoodsRequest);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @ResponseBody
    @GetMapping("/search")
    public void search() {
        validateModuleStorage.importGoods(true);
        List<TicketImportGoods> data = ticketImportGoodsService.search(null, 1, null, null, null);
        if (data != null) {
            for (TicketImportGoods o : data) {
                System.out.println(o.toString());
            }
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@ModelAttribute("goodsImport") TicketImportGoods ticketImportGoods,
                         @PathVariable("id") Integer importId,
                         HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportGoodsService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportGoodsService.update(ticketImportGoods, importId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/reset/{id}")
    public ModelAndView clear(@PathVariable("id") Integer draftImportId) {
        validateModuleStorage.importGoods(true);
        if (draftImportId <= 0 || ticketImportGoodsService.findById(draftImportId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportGoodsService.delete(draftImportId);
        return new ModelAndView("redirect:");
    }

    @PostMapping("/send-approval/{id}")
    public ModelAndView sendApproval(@PathVariable("id") Integer importId) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportGoodsService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportGoodsService.updateStatus(importId, "");
        return new ModelAndView("redirect:");
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approve(@PathVariable("id") Integer importId) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportGoodsService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportGoodsService.updateStatus(importId, "");
        return new ModelAndView("redirect:");
    }
}