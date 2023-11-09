package com.flowiee.app.product.controller;

import com.flowiee.app.category.CategoryService;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.product.entity.Product;
import com.flowiee.app.product.entity.ProductAttribute;
import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.product.model.TrangThai;
import com.flowiee.app.storage.entity.FileStorage;
import com.flowiee.app.storage.service.FileStorageService;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.system.service.NotificationService;
import com.flowiee.app.product.entity.Price;
import com.flowiee.app.product.services.*;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.KiemTraQuyenModuleSanPham;
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
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

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
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleSanPham validateRole;

    @GetMapping(value = "")
    public ModelAndView viewAllProducts() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (validateRole.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM);
            modelAndView.addObject("sanPham", new Product());
            modelAndView.addObject("listSanPham", productsService.findAll());
            modelAndView.addObject("listLoaiSanPham", categoryService.findSubCategory(CategoryUtil.PRODUCTTYPE));
            modelAndView.addObject("listDonViTinh", categoryService.findSubCategory(CategoryUtil.UNIT));
            modelAndView.addObject("templateImportName", FileUtil.TEMPLATE_I_SANPHAM);
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            if (validateRole.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (validateRole.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (validateRole.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping(value = "/{id}")
    public ModelAndView viewGeneralProduct(@PathVariable("id") Integer sanPhamId) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM_TONG_QUAN);
        modelAndView.addObject("sanPham", new Product());
        modelAndView.addObject("bienTheSanPham", new ProductVariant());
        modelAndView.addObject("giaSanPham", new Price());
        modelAndView.addObject("idSanPham", sanPhamId);
        // Load chi tiết thông tin sản phẩm
        modelAndView.addObject("detailProducts", productsService.findById(sanPhamId));
        // Danh sách loại sản phẩm
        modelAndView.addObject("listTypeProducts", categoryService.findSubCategory(CategoryUtil.PRODUCTTYPE));
        // Danh sách màu sắc
        modelAndView.addObject("listDmMauSacSanPham", categoryService.findSubCategory(CategoryUtil.COLOR));
        // Danh sách kích cỡ
        modelAndView.addObject("listDmKichCoSanPham", categoryService.findSubCategory(CategoryUtil.SIZE));
        // Load danh sách biến thể sản phẩm
        modelAndView.addObject("listBienTheSanPham", productVariantService.getListVariantOfProduct(sanPhamId));
        // Danh sách đơn vị tính
        modelAndView.addObject("listDonViTinh", categoryService.findSubCategory(CategoryUtil.UNIT));
        // Danh sách chất liệu vải
        modelAndView.addObject("listDmChatLieuVai", categoryService.findSubCategory(CategoryUtil.FABRICTYPE));
        //List image
        modelAndView.addObject("listImageOfSanPham", fileStorageService.getImageOfSanPham(sanPhamId));
        //Image active
        FileStorage imageActive = fileStorageService.findImageActiveOfSanPham(sanPhamId);
        modelAndView.addObject("imageActive", imageActive != null ? imageActive : new FileStorage());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
        return modelAndView;
    }

    @GetMapping(value = "/variant/{id}")
    public ModelAndView viewDetailProduct(@PathVariable("id") Integer bienTheSanPhamId) {
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
    public String insertProductOriginal(HttpServletRequest request, @ModelAttribute("sanPham") Product product) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        productsService.save(product);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/insert")
    public String insertProductVariant(HttpServletRequest request, @ModelAttribute("bienTheSanPham") ProductVariant productVariant) {
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

    @PostMapping(value = "/attribute/insert")
    public String insertProductAttribute(HttpServletRequest request, @ModelAttribute("thuocTinhSanPham") ProductAttribute productAttribute) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        productAttributeService.save(productAttribute);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/update/{id}")
    public String updateProductOriginal(HttpServletRequest request, @ModelAttribute("sanPham") Product product, @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || productsService.findById(id) == null) {
            throw new BadRequestException();
        }
        productsService.update(product, id);


        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/variant/update/{id}")
    public String updateProductVariant(HttpServletRequest request, @PathVariable("id") Integer id) {
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

    @Transactional
    @PostMapping(value = "/attribute/update/{id}")
    public String updateProductAttribute(@ModelAttribute("thuocTinhSanPham") ProductAttribute attribute,
                                  @PathVariable("id") Integer id,
                                  HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        attribute.setId(id);
        productAttributeService.update(attribute, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteProductOriginal(HttpServletRequest request, @PathVariable("id") Integer id) {
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

    @PostMapping(value = "/variant/delete/{id}")
    public String deleteProductVariant(HttpServletRequest request, @PathVariable("variantID") Integer variantID) {
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

    @PostMapping(value = "/attribute/delete/{id}")
    public String deleteAttribute(@ModelAttribute("attribute") ProductAttribute attribute,
                                  @PathVariable("id") Integer attributeId,
                                  HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (productAttributeService.findById(attributeId) != null) {
            productAttributeService.delete(attributeId);
            return "redirect:" + request.getHeader("referer");
        } else {
            throw new NotFoundException();
        }
    }

    @PostMapping(value = "/active-image/{sanPhamId}")
    public String activeImageOfProductOriginal(HttpServletRequest request,
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

    @PostMapping(value = "/variant/active-image/{sanPhamBienTheId}")
    public String activeImageOfProductVariant(HttpServletRequest request,
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

    @PostMapping(value = "/gia-ban/update/{id}")
    public String updateProductPrice(HttpServletRequest request,
                                     @ModelAttribute("price") Price price,
                                     @PathVariable("id") Integer idBienTheSanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (validateRole.kiemTraQuyenQuanLyGiaBan()) {
            int idGiaBanHienTai = Integer.parseInt(request.getParameter("idGiaBan"));
            priceService.update(price, idBienTheSanPham, idGiaBanHienTai);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (validateRole.kiemTraQuyenExport()) {
            byte[] dataExport = productsService.exportData(null);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_E_SANPHAM + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}