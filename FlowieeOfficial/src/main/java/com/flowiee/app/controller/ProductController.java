package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.category.CategoryService;
import com.flowiee.app.config.author.ValidateModuleProduct;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.service.product.PriceService;
import com.flowiee.app.service.product.ProductAttributeService;
import com.flowiee.app.service.product.ProductService;
import com.flowiee.app.service.product.ProductVariantService;
import com.flowiee.app.service.storage.FileStorageService;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.Price;
import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.ProductAttribute;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.model.product.TrangThai;
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
public class ProductController extends BaseController {
    @Autowired
    private ProductService productsService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ValidateModuleProduct validateModuleProduct;

    @GetMapping(value = "")
    public ModelAndView viewAllProducts() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.readProduct()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_PRODUCT);
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("listSanPham", productsService.findAll());
        modelAndView.addObject("listProductType", categoryService.findSubCategory(CategoryUtil.PRODUCTTYPE));
        modelAndView.addObject("listDonViTinh", categoryService.findSubCategory(CategoryUtil.UNIT));
        modelAndView.addObject("listBrand", categoryService.findSubCategory(CategoryUtil.BRAND));
        modelAndView.addObject("templateImportName", FileUtil.TEMPLATE_I_SANPHAM);
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
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_PRODUCT_INFO);
        modelAndView.addObject("sanPham", new Product());
        modelAndView.addObject("bienTheSanPham", new ProductVariant());
        modelAndView.addObject("giaSanPham", new Price());
        modelAndView.addObject("idSanPham", productId);
        // Load chi tiết thông tin sản phẩm
        modelAndView.addObject("detailProducts", productsService.findById(productId));
        // Danh sách loại sản phẩm
        modelAndView.addObject("listTypeProducts", categoryService.findSubCategory(CategoryUtil.PRODUCTTYPE));
        // Danh sách màu sắc
        modelAndView.addObject("listDmMauSacSanPham", categoryService.findSubCategory(CategoryUtil.COLOR));
        // Danh sách kích cỡ
        modelAndView.addObject("listDmKichCoSanPham", categoryService.findSubCategory(CategoryUtil.SIZE));
        // Load danh sách biến thể sản phẩm
        modelAndView.addObject("listBienTheSanPham", productVariantService.getListVariantOfProduct(productId));
        // Danh sách đơn vị tính
        modelAndView.addObject("listDonViTinh", categoryService.findSubCategory(CategoryUtil.UNIT));
        // Danh sách chất liệu vải
        modelAndView.addObject("listDmChatLieuVai", categoryService.findSubCategory(CategoryUtil.FABRICTYPE));
        modelAndView.addObject("listBrand", categoryService.findSubCategory(CategoryUtil.FABRICTYPE));
        //List image
        modelAndView.addObject("listImageOfSanPham", fileStorageService.getImageOfSanPham(productId));
        //Image active
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPham(productId);
        modelAndView.addObject("imageActive", imageActive != null ? imageActive : new FileStorage());        
        return baseView(modelAndView);
    }

    @GetMapping(value = "/variant/{id}")
    public ModelAndView viewDetailProduct(@PathVariable("id") Integer bienTheSanPhamId) {
        // Show trang chi tiết của biến thể
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
        modelAndView.addObject("listThuocTinh", productAttributeService.getAllAttributes(bienTheSanPhamId));
        modelAndView.addObject("bienTheSanPhamId", bienTheSanPhamId);
        modelAndView.addObject("bienTheSanPham", productVariantService.findById(bienTheSanPhamId));
        modelAndView.addObject("listImageOfSanPhamBienThe", fileStorageService.getImageOfSanPhamBienThe(bienTheSanPhamId));
        modelAndView.addObject("listPrices", priceService.findPricesByProductVariant(bienTheSanPhamId));
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPhamBienThe(bienTheSanPhamId);
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
    public String insertProductVariant(HttpServletRequest request, @ModelAttribute("bienTheSanPham") ProductVariant productVariant) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        productVariant.setTrangThai(TrangThai.KINH_DOANH.name());
        productVariant.setMaSanPham(FlowieeUtil.now("yyyyMMddHHmmss"));
        productVariantService.save(productVariant);
        //Khởi tạo giá default của giá bán
        priceService.save(Price.builder().productVariant(productVariant).giaBan(0D).trangThai(true).build());
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

    @Transactional
    @PostMapping(value = "/update/{id}")
    public String updateProductOriginal(HttpServletRequest request, @ModelAttribute("sanPham") Product product, @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (product == null|| id <= 0 || productsService.findById(id) == null) {
            throw new NotFoundException("Product not found!");
        }
        productsService.update(product, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/update/{id}")
    public String updateProductVariant(HttpServletRequest request, @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productVariantService.findById(id) == null) {
            throw new NotFoundException("Product variant not found!");
        }
        productVariantService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/attribute/update/{id}")
    public String updateProductAttribute(@ModelAttribute("thuocTinhSanPham") ProductAttribute attribute,
                                        @PathVariable("id") Integer id,
                                        HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (id <= 0 || productAttributeService.findById(id) == null) {
            throw new NotFoundException("Product attribute not found!");
        }
        attribute.setId(id);
        productAttributeService.update(attribute, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteProductOriginal(HttpServletRequest request, @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.deleteProduct()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (productsService.findById(id) == null) {
            throw new NotFoundException("Product not found!");
        }
        productsService.delete(id);
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
                                               @PathVariable("sanPhamId") Integer sanPhamId,
                                               @RequestParam("imageId") Integer imageId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateImage()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (sanPhamId == null || sanPhamId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException("Product or image not found!");
        }
        fileStorageService.setImageActiveOfSanPham(sanPhamId, imageId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/active-image/{sanPhamBienTheId}")
    public String activeImageOfProductVariant(HttpServletRequest request,
                                              @PathVariable("sanPhamBienTheId") Integer sanPhamBienTheId,
                                              @RequestParam("imageId") Integer imageId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateImage()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (sanPhamBienTheId == null || sanPhamBienTheId <= 0 || imageId == null || imageId <= 0) {
            throw new NotFoundException("Product variant or image not found!");
        }
        fileStorageService.setImageActiveOfBienTheSanPham(sanPhamBienTheId, imageId);
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
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_E_SANPHAM + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_UNAUTHORIZED);
        }
    }
}