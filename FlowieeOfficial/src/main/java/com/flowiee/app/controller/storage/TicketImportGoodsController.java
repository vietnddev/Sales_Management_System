package com.flowiee.app.controller.storage;

import com.flowiee.app.config.author.ValidateModuleStorage;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.TicketImportGoods;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.MaterialTemp;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.entity.ProductVariantTemp;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.product.GoodsImportRequest;
import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.category.Category;
import com.flowiee.app.category.CategoryService;
import com.flowiee.app.service.product.GoodsImportService;
import com.flowiee.app.service.product.MaterialService;
import com.flowiee.app.service.product.MaterialTempService;
import com.flowiee.app.service.product.ProductVariantService;
import com.flowiee.app.service.product.ProductVariantTempService;
import com.flowiee.app.service.product.SupplierService;
import com.flowiee.app.service.system.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/storage/goods")
public class TicketImportGoodsController extends BaseController {
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
    private CategoryService categoryService;
    @Autowired
    private ValidateModuleStorage validateModuleStorage;

    @GetMapping("")
    public ModelAndView loadPage() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (validateModuleStorage.goodsImport()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_STORAGE_DRAFT_IMPORT);
            TicketImportGoods ticketImportGoodsPresent = goodsImportService.findDraftImportPresent(FlowieeUtil.ACCOUNT_ID);
            if (ticketImportGoodsPresent == null) {
                ticketImportGoodsPresent = goodsImportService.createDraftImport();
            }
            modelAndView.addObject("goodsImportRequest", new GoodsImportRequest());
            modelAndView.addObject("goodsImport", new TicketImportGoods());
            modelAndView.addObject("draftGoodsImport", ticketImportGoodsPresent);
            modelAndView.addObject("orderTime", ticketImportGoodsPresent.getOrderTime().toString().substring(0, 10));
            modelAndView.addObject("receivedTime", ticketImportGoodsPresent.getReceivedTime().toString().substring(0, 10));
            modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
            modelAndView.addObject("listBienTheSanPhamSelected", bienTheSanPhamServiceTemp.findByImportId(ticketImportGoodsPresent.getId()));
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
                listHinhThucThanhToan.addAll(categoryService.findSubCategory(CategoryUtil.PAYMETHOD));
            } else {
                listHinhThucThanhToan.add(ticketImportGoodsPresent.getPaymentMethod());
                List<Category> listHinhThucThanhToanTemp = categoryService.findSubCategory(CategoryUtil.PAYMETHOD);
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
                listTrangThaiThanhToan.putAll(FlowieeUtil.getPaymentStatusCategory());
            } else {
                listTrangThaiThanhToan.put(ticketImportGoodsPresent.getPaidStatus(), FlowieeUtil.getPaymentStatusCategory().get(ticketImportGoodsPresent.getPaidStatus()));
                Map<String, String> listTrangThaiThanhToanTemp = FlowieeUtil.getPaymentStatusCategory();
                listTrangThaiThanhToanTemp.remove(ticketImportGoodsPresent.getPaidStatus());
                listTrangThaiThanhToan.putAll(listTrangThaiThanhToanTemp);
            }
            modelAndView.addObject("listTrangThaiThanhToan", listTrangThaiThanhToan);
            
            //
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/draft/add-product/{importId}")
    public String addProductVariantToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (importId <= 0 || goodsImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import to add product not found!");
        }
        List<String> listProductVariantId = Arrays.stream(request.getParameterValues("productVariantId")).toList();
        for (String productVariantId : listProductVariantId) {
            ProductVariant productVariant = productVariantService.findById(Integer.parseInt(productVariantId));
            productVariant.setTicketImportGoods(new TicketImportGoods(importId));
            
            ProductVariantTemp temp = bienTheSanPhamServiceTemp.findProductVariantInGoodsImport(importId, productVariant.getId());
            if (temp != null) {            	
            	bienTheSanPhamServiceTemp.updateSoLuong(temp.getSoLuongKho() + 1, temp.getId());
            } else {
            	bienTheSanPhamServiceTemp.save(ProductVariantTemp.convertFromProductVariant(productVariant));
            }         
        }
        return "redirect:/storage/goods";
    }

    @PostMapping("/draft/add-material/{importId}")
    public String addMaterialToDraftImport(@PathVariable("importId") Integer importId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (importId <= 0 || goodsImportService.findById(importId) == null) {
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
    public String update(@ModelAttribute("goodsImportRequest") GoodsImportRequest goodsImportRequest, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (validateModuleStorage.goodsImport()) {
            goodsImportRequest.setOrderTime(FlowieeUtil.convertStringToDate(request.getParameter("orderTime_"), "yyyy-MM-dd"));
            goodsImportRequest.setReceivedTime(FlowieeUtil.convertStringToDate(request.getParameter("receivedTime_"), "yyyy-MM-dd"));
            goodsImportService.saveDraft(goodsImportRequest);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @ResponseBody
    @GetMapping("/search")
    public void search() {
        List<TicketImportGoods> data = goodsImportService.search(null, 1, null, null, null);
        if (data != null) {
            for (TicketImportGoods o : data) {
                System.out.println(o.toString());
            }
        }
    }

    @GetMapping("/update/{id}")
    public String update(@ModelAttribute("goodsImport") TicketImportGoods ticketImportGoods,
                            @PathVariable("id") Integer importId,
                            HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!validateModuleStorage.goodsImport()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (importId <= 0 || goodsImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        goodsImportService.update(ticketImportGoods, importId);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/reset/{id}")
    public String clear(@PathVariable("id") Integer draftImportId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (draftImportId <= 0 || goodsImportService.findById(draftImportId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        if (!validateModuleStorage.goodsImport()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        goodsImportService.delete(draftImportId);
        return "redirect:";
    }

    @PostMapping("/send-approval/{id}")
    public String sendApproval(@PathVariable("id") Integer importId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (importId <= 0 || goodsImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        if (!validateModuleStorage.goodsImport()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        goodsImportService.updateStatus(importId, "");
        return "redirect:";
    }

    @PostMapping("/approve/{id}")
    public String approve(@PathVariable("id") Integer importId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!validateModuleStorage.goodsImport()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (importId <= 0 || goodsImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        goodsImportService.updateStatus(importId, "");
        return "redirect:";
    }
}