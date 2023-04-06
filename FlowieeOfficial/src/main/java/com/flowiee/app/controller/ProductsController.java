package com.flowiee.app.controller;

import com.flowiee.app.model.*;
import com.flowiee.app.services.*;
import com.flowiee.app.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/sales/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PriceHistoryService priceHistoryService;

    @GetMapping(value = "")
    public String getAllProducts(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("products", new Products());
            modelMap.addAttribute("listProducts", productsService.getAllProducts());
            modelMap.addAttribute("listTypeProducts", categoryService.getListCategory("typeProduct"));
            return "pages/sales/products";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @GetMapping(value = "/{productID}") // Show trang tổng quan chi tiết của một sản phẩm
    public String getDetailProduct(ModelMap modelMap, @PathVariable("productID") int productID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("products", new Products());
            modelMap.addAttribute("product_variants", new Product_Variants());
            modelMap.addAttribute("productID", productID);
            // Load chi tiết thông tin sản phẩm
            modelMap.addAttribute("detailProducts", productsService.getProductByID(productID));
            // Danh sách loại sản phẩm từ danh mục hệ thống
            modelMap.addAttribute("listTypeProducts", categoryService.getListCategory("typeProduct"));
            // Danh sách màu sắc từ danh mục hệ thống
            modelMap.addAttribute("listColorProducts", categoryService.getListCategory("colorProduct"));
            // Load danh sách biến thể màu sắc
            modelMap.addAttribute("listColorVariant", productVariantService.getListVariantOfProduct("Color", productID));
            return "pages/sales/product_detail";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @GetMapping(value = "{productID}/variants/{productVariantID}") // Show trang chi tiết của biến thể
    public String getDetailProductVariant(ModelMap modelMap, @PathVariable("productVariantID") int productVariantID) {
        /* Show trang chi tiết của biến thể
         * */
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("product_variants", new Product_Variants());
            modelMap.addAttribute("product_attributes", new Product_Attributes());
            modelMap.addAttribute("listAttributes", productAttributeService.getAllAttributes(productVariantID));
            modelMap.addAttribute("productVariantID", productVariantID);
            //Lấy danh sách hình ảnh của biến thể
            modelMap.addAttribute("listFiles", imageService.getFilesByProductVariant(productVariantID));
            return "pages/sales/product_variant";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/insert") // Thêm mới sản phẩm
    public String insertProduct(HttpServletRequest request, @ModelAttribute("products") Products products) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            productsService.insertProduct(products);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @PostMapping(value = "/variants/insert") // Thêm mới biến thể cho sản phẩm
    public String insertVariants(HttpServletRequest request, @ModelAttribute("product_variants") Product_Variants productVariant) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            productVariant.setName("Color");
            productVariantService.insertVariant(productVariant);

            //Thêm giá bán
            priceHistoryService.save(new PriceHistory(productVariant.getProductVariantID(), 0));

            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/variants/attributes/insert") // Thêm mới thuộc tính cho biến thể
    public String insertAttributes(HttpServletRequest request, @ModelAttribute("product_attributes") Product_Attributes productAttribute) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            productAttributeService.saveAttribute(productAttribute);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @PostMapping(value = "/update/{ID}")
    public String updateProduct(HttpServletRequest request, @ModelAttribute("products") Products products) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            productsService.updateProduct(products);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    //Cập nhật thuộc tính cho sản phẩm
    @Transactional
    @PostMapping(value = "/attribute/update/{ID}", params = "update")
    public String updateAttribute(@ModelAttribute("attribute") Product_Attributes attribute,
                                  HttpServletRequest request, @PathVariable("ID") int attributeID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            attribute.setProductAttributeID(attributeID);
            productAttributeService.saveAttribute(attribute);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    //Lock attribute
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/attribute/update/{ID}", params = "lock")
    public String lockAttribute(@ModelAttribute("attribute") Product_Attributes attribute,
                                  HttpServletRequest request, @PathVariable("ID") int attributeID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            attribute.setProductAttributeID(attributeID);
            if (attribute.isStatus()){
                attribute.setStatus(false);
            } else {
                attribute.setStatus(true);
            }
            productAttributeService.saveAttribute(attribute);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    //Xóa thuộc tính
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/attribute/delete/{ID}")
    public String deleteAttribute(@ModelAttribute("attribute") Product_Attributes attribute,
                                  HttpServletRequest request, @PathVariable("ID") int attributeID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            productAttributeService.deleteAttribute(attributeID);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/delete/{productID}")
    public String deleteProduct(HttpServletRequest request, @PathVariable("productID") int productID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (productsService.getProductByID(productID).isPresent()) {
                productsService.deleteProduct(productID);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Product not found!");
            }
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/variants/delete/{variantID}")
    public String deleteVariants(HttpServletRequest request, @PathVariable("variantID") int variantID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (productVariantService.getByVariantID(variantID).isPresent()) {
                productVariantService.deteleVariant(variantID);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Record not found!");
            }
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/variants/attributes/delete/{attributeID}")
    public String deleteAttributes(HttpServletRequest request, @PathVariable("attributeID") int attributeID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (productAttributeService.getByAttributeID(attributeID).isPresent()) {
                productAttributeService.deleteAttribute(attributeID);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Record not found!");
            }
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }
}