package com.flowiee.app.controller.ui;

import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.TicketImport;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.MaterialTemp;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.entity.ProductVariantTemp;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.request.TicketImportGoodsRequest;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.*;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/storage/ticket-import")
public class TicketImportUIController extends BaseController {
    @Autowired private TicketImportService ticketImportService;
    @Autowired private SupplierService     supplierService;
    @Autowired private ProductService productService;
    @Autowired private ProductVariantTempService productVariantServiceTemp;
    @Autowired private MaterialService materialService;
    @Autowired private MaterialTempService materialServiceTemp;
    @Autowired private CategoryService categoryService;
    @Autowired private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView loadPage() {
        validateModuleStorage.importGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_IMPORT);
        return baseView(modelAndView);
    }

    @GetMapping("/create")
    public ModelAndView loadPageCreate() {
        validateModuleStorage.importGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_IMPORT_CREATE);
        TicketImport ticketImportPresent = ticketImportService.findDraftImportPresent(CommonUtils.getCurrentAccountId());
        if (ticketImportPresent == null) {
            ticketImportPresent = ticketImportService.createDraftImport();
        }
        modelAndView.addObject("goodsImportRequest", new TicketImportGoodsRequest());
        modelAndView.addObject("goodsImport", new TicketImport());
        modelAndView.addObject("draftGoodsImport", ticketImportPresent);
        modelAndView.addObject("orderTime", ticketImportPresent.getOrderTime().toString().substring(0, 10));
        modelAndView.addObject("receivedTime", ticketImportPresent.getReceivedTime().toString().substring(0, 10));
        modelAndView.addObject("listBienTheSanPham", productService.findAllProductVariants());
        modelAndView.addObject("listBienTheSanPhamSelected", productVariantServiceTemp.findByImportId(ticketImportPresent.getId()));
        modelAndView.addObject("listMaterial", materialService.findAll());
        modelAndView.addObject("listMaterialSelected", materialServiceTemp.findByImportId(ticketImportPresent.getId()));

        List<Supplier> listSupplier = new ArrayList<>();
        if (ticketImportPresent.getSupplier() == null) {
            listSupplier.add(new Supplier(null, "Chọn supplier"));
            listSupplier.addAll(supplierService.findAll());
        } else {
            listSupplier.add(ticketImportPresent.getSupplier());
            List<Supplier> listSupplierTemp = supplierService.findAll();
            listSupplierTemp.remove(ticketImportPresent.getSupplier());
            listSupplier.addAll(listSupplierTemp);
        }
        modelAndView.addObject("listSupplier", listSupplier);

        List<Category> listHinhThucThanhToan = new ArrayList<>();
        if (ticketImportPresent.getPaymentMethod() == null) {
            listHinhThucThanhToan.add(new Category(null, "Chọn hình thức thanh toán"));
            listHinhThucThanhToan.addAll(categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName(), null));
        } else {
            listHinhThucThanhToan.add(ticketImportPresent.getPaymentMethod());
            List<Category> listHinhThucThanhToanTemp = categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName(), null);
            listHinhThucThanhToanTemp.remove(ticketImportPresent.getPaymentMethod());
            listHinhThucThanhToan.addAll(listHinhThucThanhToanTemp);
        }
        modelAndView.addObject("listHinhThucThanhToan", listHinhThucThanhToan);

        List<Account> listAccount = new ArrayList<>();
        if (ticketImportPresent.getReceivedBy() == null) {
            listAccount.add(new Account(null, null, "Chọn người nhập hàng"));
            listAccount.addAll(accountService.findAll());
        } else {
            listAccount.add(ticketImportPresent.getReceivedBy());
            List<Account> lístAccountTemp = accountService.findAll();
            lístAccountTemp.remove(ticketImportPresent.getReceivedBy());
            listAccount.addAll(lístAccountTemp);
        }
        modelAndView.addObject("listNhanVien", listAccount);

        Map<String, String> listTrangThaiThanhToan = new HashMap<>();
        if (ticketImportPresent.getPaidStatus() == null || ticketImportPresent.getPaidStatus().isEmpty()) {
            listTrangThaiThanhToan.put(null, "Chọn trạng thái thanh toán");
            listTrangThaiThanhToan.putAll(CommonUtils.getPaymentStatusCategory());
        } else {
            listTrangThaiThanhToan.put(ticketImportPresent.getPaidStatus(), CommonUtils.getPaymentStatusCategory().get(ticketImportPresent.getPaidStatus()));
            Map<String, String> listTrangThaiThanhToanTemp = CommonUtils.getPaymentStatusCategory();
            listTrangThaiThanhToanTemp.remove(ticketImportPresent.getPaidStatus());
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
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import to add product not found!");
        }
        List<String> listProductVariantId = Arrays.stream(request.getParameterValues("productVariantId")).toList();
        for (String productVariantId : listProductVariantId) {
            ProductVariant productVariant =  productService.findProductVariantById(Integer.parseInt(productVariantId));
            productVariant.setTicketImport(new TicketImport(importId));

            ProductVariantTemp temp = productVariantServiceTemp.findProductVariantInGoodsImport(importId, productVariant.getId());
            if (temp != null) {
                productVariantServiceTemp.updateSoLuong(temp.getSoLuongKho() + 1, temp.getId());
            } else {
                productVariantServiceTemp.save(ProductVariantTemp.convertFromProductVariant(productVariant));
            }
        }
        return new ModelAndView("redirect:/storage/ticket-import");
    }

    @PostMapping("/draft/add-material/{importId}")
    public String addMaterialToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import to add material not found!");
        }
        List<String> listMaterialId = Arrays.stream(request.getParameterValues("materialId")).toList();
        for (String materialId : listMaterialId) {
            Material material = materialService.findById(Integer.parseInt(materialId));
            material.setTicketImport(new TicketImport(importId));

            MaterialTemp temp = materialServiceTemp.findMaterialInGoodsImport(importId, material.getId());
            if (temp != null) {
                temp.setQuantity(temp.getQuantity() + 1);
                materialServiceTemp.update(temp, temp.getId());
            } else {
                materialServiceTemp.save(MaterialTemp.convertFromMaterial(material));
            }
        }
        return "redirect:/storage/ticket-import";
    }

    @PostMapping("/draft/save")
    public ModelAndView update(@ModelAttribute("goodsImportRequest") TicketImportGoodsRequest ticketImportGoodsRequest, HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        ticketImportGoodsRequest.setOrderTime(DateUtils.convertStringToDate(request.getParameter("orderTime_"), "yyyy-MM-dd"));
        ticketImportGoodsRequest.setReceivedTime(DateUtils.convertStringToDate(request.getParameter("receivedTime_"), "yyyy-MM-dd"));
        ticketImportService.saveDraft(ticketImportGoodsRequest);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @ResponseBody
    @GetMapping("/search")
    public void search() {
        validateModuleStorage.importGoods(true);
        List<TicketImport> data = ticketImportService.findAll(null, 1, null, null, null);
        if (data != null) {
            for (TicketImport o : data) {
                System.out.println(o.toString());
            }
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@ModelAttribute("goodsImport") TicketImport ticketImport,
                         @PathVariable("id") Integer importId,
                         HttpServletRequest request) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.update(ticketImport, importId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/reset/{id}")
    public ModelAndView clear(@PathVariable("id") Integer draftImportId) {
        validateModuleStorage.importGoods(true);
        if (draftImportId <= 0 || ticketImportService.findById(draftImportId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.delete(draftImportId);
        return new ModelAndView("redirect:");
    }

    @PostMapping("/send-approval/{id}")
    public ModelAndView sendApproval(@PathVariable("id") Integer importId) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.updateStatus(importId, "");
        return new ModelAndView("redirect:");
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approve(@PathVariable("id") Integer importId) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.updateStatus(importId, "");
        return new ModelAndView("redirect:");
    }
}