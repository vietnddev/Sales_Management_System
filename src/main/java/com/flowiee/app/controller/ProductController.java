package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.service.*;
import com.flowiee.app.security.author.ValidateModuleProduct;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.PagesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/san-pham")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productsService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherApplyService voucherApplyService;
    @Autowired
    private VoucherTicketService voucherTicketService;
    @Autowired
    private ValidateModuleProduct validateModuleProduct;

    //@PreAuthorize("@validateModuleProduct.readProduct()")
    @GetMapping
    public ModelAndView viewAllProducts() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.readProduct()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        List<Category> brands = new ArrayList<>();
        List<Category> productTypes = new ArrayList<>();
        List<Category> units = new ArrayList<>();
        categoryService.findSubCategory(Arrays.asList(AppConstants.BRAND, AppConstants.PRODUCTTYPE, AppConstants.UNIT)).forEach(category -> {
            if (AppConstants.BRAND.equals(category.getType())) {
                brands.add(category);
            }
            if (AppConstants.PRODUCTTYPE.equals(category.getType())) {
                productTypes.add(category);
            }
            if (AppConstants.UNIT.equals(category.getType())) {
                units.add(category);
            }
        });
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_PRODUCT);
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("listSanPham", productsService.findAll(null, null));
        modelAndView.addObject("listVoucherInfo", voucherService.findAll());
        modelAndView.addObject("listProductType", productTypes);
        modelAndView.addObject("listDonViTinh", units);
        modelAndView.addObject("listBrand", brands);
        modelAndView.addObject("templateImportName", AppConstants.TEMPLATE_I_SANPHAM);
        if (validateModuleProduct.insertProduct()) {
            modelAndView.addObject("action_create", "enable");
        }
        if (validateModuleProduct.updateProduct()) {
            modelAndView.addObject("action_update", "enable");
        }
        if (validateModuleProduct.deleteProduct()) {
            modelAndView.addObject("action_delete", "enable");
        }
        return baseView(modelAndView);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView viewGeneralProduct(@PathVariable("id") Integer productId) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.readProduct()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        if (productId <= 0 || productsService.findById(productId) == null) {
            throw new NotFoundException("Product not found!");
        }
        List<Category> productTypes = new ArrayList<>();
        List<Category> fabricTypes = new ArrayList<>();
        List<Category> brands = new ArrayList<>();
        List<Category> colors = new ArrayList<>();
        List<Category> sizes = new ArrayList<>();
        List<Category> units = new ArrayList<>();
        categoryService.findSubCategory(Arrays.asList(AppConstants.BRAND, AppConstants.PRODUCTTYPE, AppConstants.COLOR,
                                                      AppConstants.SIZE, AppConstants.FABRICTYPE, AppConstants.UNIT)).forEach(category -> {
            if (AppConstants.PRODUCTTYPE.equals(category.getType())) {
                productTypes.add(category);
            }
            if (AppConstants.FABRICTYPE.equals(category.getType())) {
                fabricTypes.add(category);
            }
            if (AppConstants.BRAND.equals(category.getType())) {
                brands.add(category);
            }
            if (AppConstants.COLOR.equals(category.getType())) {
                colors.add(category);
            }
            if (AppConstants.SIZE.equals(category.getType())) {
                sizes.add(category);
            }
            if (AppConstants.UNIT.equals(category.getType())) {
                units.add(category);
            }
        });

        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_PRODUCT_INFO);
        modelAndView.addObject("sanPham", new Product());
        modelAndView.addObject("bienTheSanPham", new ProductVariant());
        modelAndView.addObject("giaSanPham", new Price());
        modelAndView.addObject("idSanPham", productId);
        modelAndView.addObject("detailProducts", ProductDTO.fromProduct(productsService.findById(productId)));
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAllProductVariantOfProduct(productId));
        modelAndView.addObject("listTypeProducts", productTypes);
        modelAndView.addObject("listDmChatLieuVai", fabricTypes);
        modelAndView.addObject("listBrand", brands);
        modelAndView.addObject("listDmMauSacSanPham", colors);
        modelAndView.addObject("listDmKichCoSanPham", sizes);
        modelAndView.addObject("listDonViTinh", units);
        //List image
        modelAndView.addObject("listImageOfSanPham", fileStorageService.getImageOfSanPham(productId));
        //Image active
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPham(productId);
        modelAndView.addObject("imageActive", imageActive != null ? imageActive : new FileStorage());        
        return baseView(modelAndView);
    }

    @GetMapping(value = "/variant/{id}")
    public ModelAndView viewDetailProduct(@PathVariable("id") Integer variantId) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.readProduct()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_PRODUCT_VARIANT);
        modelAndView.addObject("bienTheSanPham", new ProductVariant());
        modelAndView.addObject("thuocTinhSanPham", new ProductAttribute());
        modelAndView.addObject("giaBanSanPham", new Price());
        modelAndView.addObject("listThuocTinh", productAttributeService.getAllAttributes(variantId));
        modelAndView.addObject("bienTheSanPhamId", variantId);
        modelAndView.addObject("bienTheSanPham", productVariantService.findById(variantId));
        modelAndView.addObject("listImageOfSanPhamBienThe", fileStorageService.getImageOfSanPhamBienThe(variantId));
        modelAndView.addObject("listPrices", priceService.findPricesByProductVariant(variantId));
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPhamBienThe(variantId);
        if (imageActive == null) {
            imageActive = new FileStorage();
        }
        modelAndView.addObject("imageActive", imageActive);        
        return baseView(modelAndView);
    }

    @PostMapping(value = "/insert")
    public String insertProductOriginal(HttpServletRequest request, @ModelAttribute("sanPham") Product product) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.insertProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        productsService.save(product);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/insert")
    public String insertProductVariant(HttpServletRequest request,
                                       @ModelAttribute("bienTheSanPham") ProductVariant productVariant,
                                       @RequestBody Float price) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        productVariant.setTrangThai(AppConstants.PRODUCT_STATUS.A.name());
        productVariant.setMaSanPham(CommonUtil.now("yyyyMMddHHmmss"));
        productVariantService.save(productVariant);
        //Khởi tạo giá default của giá bán
        priceService.save(Price.builder().productVariant(productVariant).giaBan(0D).status(AppConstants.PRICE_STATUS.ACTIVE.name()).build());
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/attribute/insert")
    public String insertProductAttribute(HttpServletRequest request, @ModelAttribute("thuocTinhSanPham") ProductAttribute productAttribute) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        productAttributeService.save(productAttribute);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/update/{id}")
    public String updateProductOriginal(HttpServletRequest request,
                                        @ModelAttribute("sanPham") Product product,
                                        @PathVariable("id") Integer productId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (product == null|| productId <= 0 || productsService.findById(productId) == null) {
            throw new NotFoundException("Product not found!");
        }
        productsService.update(product, productId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/update/{id}")
    public String updateProductVariant(HttpServletRequest request, @PathVariable("id") Integer variantId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productVariantService.findById(variantId) == null) {
            throw new NotFoundException("Product variant not found!");
        }
        productVariantService.delete(variantId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/attribute/update/{id}")
    public String updateProductAttribute(@ModelAttribute("thuocTinhSanPham") ProductAttribute attribute,
                                        @PathVariable("id") Integer attributeId,
                                        HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (attributeId <= 0 || productAttributeService.findById(attributeId) == null) {
            throw new NotFoundException("Product attribute not found!");
        }
        attribute.setId(attributeId);
        productAttributeService.update(attribute, attributeId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteProductOriginal(HttpServletRequest request, @PathVariable("id") Integer productId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.deleteProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productsService.findById(productId) == null) {
            throw new NotFoundException("Product not found!");
        }
        productsService.delete(productId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/delete/{id}")
    public String deleteProductVariant(HttpServletRequest request, @PathVariable("id") Integer productVariantId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productVariantService.findById(productVariantId) == null) {
            throw new NotFoundException("Product variant not found!");
        }
        productVariantService.delete(productVariantId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/attribute/delete/{id}")
    public String deleteAttribute(@ModelAttribute("attribute") ProductAttribute attribute,
                                  @PathVariable("id") Integer attributeId,
                                  HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productAttributeService.findById(attributeId) == null) {
            throw new NotFoundException("Product attribute not found!");
        }
        productAttributeService.delete(attributeId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/active-image/{sanPhamId}")
    public String activeImageOfProductOriginal(HttpServletRequest request,
                                               @PathVariable("sanPhamId") Integer productId,
                                               @RequestParam("imageId") Integer imageId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateImage()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productId == null || productId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException("Product or image not found!");
        }
        fileStorageService.setImageActiveOfSanPham(productId, imageId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/active-image/{sanPhamBienTheId}")
    public String activeImageOfProductVariant(HttpServletRequest request,
                                              @PathVariable("sanPhamBienTheId") Integer productVariantId,
                                              @RequestParam("imageId") Integer imageId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateImage()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productVariantId == null || productVariantId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException("Product variant or image not found!");
        }
        fileStorageService.setImageActiveOfBienTheSanPham(productVariantId, imageId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/gia-ban/update/{id}")
    public String updateProductPrice(HttpServletRequest request,
                                     @ModelAttribute("price") Price price,
                                     @PathVariable("id") Integer productVariantId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.priceManagement()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (price == null || productVariantId <= 0 || productVariantService.findById(productVariantId) == null) {
            throw new NotFoundException("Product variant or price not found!");
        }
        int idGiaBanHienTai = Integer.parseInt(request.getParameter("idGiaBan"));
        priceService.update(price, productVariantId, idGiaBanHienTai);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleProduct.readProduct()) {
            byte[] dataExport = productsService.exportData(null);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + AppConstants.TEMPLATE_E_SANPHAM + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_UNAUTHORIZED);
        }
    }

    /** ******************** GALLERY ******************** **/
    @GetMapping("/gallery")
    public ModelAndView viewGallery() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_GALLERY);
        modelAndView.addObject("listImages", fileStorageService.getAllImageSanPham(SystemModule.PRODUCT.name()));
        modelAndView.addObject("listSanPham", productsService.findAll());
        return baseView(modelAndView);
    }

    /** ******************** VOUCHER ******************** **/
    @GetMapping("/voucher")
    public ModelAndView viewVouchers() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleProduct.readVoucher()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_VOUCHER);
            modelAndView.addObject("listVoucher", voucherService.findAll());
            modelAndView.addObject("listProduct", productsService.findAll());
            modelAndView.addObject("listVoucherType", CommonUtil.getVoucherType());
            modelAndView.addObject("voucher", new VoucherInfo());
            modelAndView.addObject("voucherDetail", new VoucherTicket());
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
    }

    @GetMapping("/voucher/detail/{id}")
    public ModelAndView viewVoucherDetail(@PathVariable("id") Integer voucherInfoId) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleProduct.readVoucher()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_VOUCHER_DETAIL);
            modelAndView.addObject("voucherDetail", voucherService.findById(voucherInfoId));
            modelAndView.addObject("listVoucherTicket", voucherTicketService.findByVoucherInfoId(voucherInfoId));
            modelAndView.addObject("voucher", new VoucherInfo());
            modelAndView.addObject("listVoucherType", CommonUtil.getVoucherType());
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
    }
    
    @PostMapping("/voucher/insert")
    public ModelAndView insertVoucher(@ModelAttribute("voucher") VoucherInfo voucherInfo,
                                      HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.insertVoucher()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            voucherInfo.setStartTime(dateFormat.parse(request.getParameter("startTime_")));
            voucherInfo.setEndTime(dateFormat.parse(request.getParameter("endTime_")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Integer> listProductToApply = new ArrayList<>();
        String[] pbienTheSP = request.getParameterValues("productToApply");
        if (pbienTheSP != null) {
            for (String id : pbienTheSP) {
                listProductToApply.add(Integer.parseInt(id));
            }
        }
        if (listProductToApply.size() > 0) {
            voucherService.save(voucherInfo, listProductToApply);
        }
        return new ModelAndView("redirect:/san-pham/voucher");
    }
}