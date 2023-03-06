package com.flowiee.app.controller.product;

import com.flowiee.app.model.sales.Product_Attributes;
import com.flowiee.app.model.sales.Product_Variants;
import com.flowiee.app.model.sales.Products;
import com.flowiee.app.services.CategoryService;
import com.flowiee.app.services.ProductAttributeService;
import com.flowiee.app.services.ProductVariantService;
import com.flowiee.app.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping(value = "")
    public String getAllProducts(ModelMap modelMap){;
        modelMap.addAttribute("products", new Products());
        modelMap.addAttribute("listProducts", productsService.getAllProducts());
        modelMap.addAttribute("listTypeProducts", categoryService.getListCategory("typeProduct"));
        return "pages/sales/products";
    }
 
    @GetMapping(value = "/{productID}") // Show trang tổng quan chi tiết của một sản phẩm
    public String getDetailProduct(ModelMap modelMap, @PathVariable("productID") int productID){
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
          
    @GetMapping(value = "{productID}/variants/{productVariantID}") // Show trang chi tiết của biến thể
    public String getDetailProductVariant(ModelMap modelMap, @PathVariable("productVariantID") int productVariantID){
        /* Show trang chi tiết của biến thể
        * */
        modelMap.addAttribute("product_variants", new Product_Variants());
        modelMap.addAttribute("product_attributes", new Product_Attributes());
        modelMap.addAttribute("listAttributes", productAttributeService.getAllAttributes(productVariantID));
        modelMap.addAttribute("productVariantID", productVariantID);
        return "pages/sales/product_variant";
    }

    @PostMapping(value = "/insert") // Thêm mới sản phẩm
    public String insertProduct(HttpServletRequest request, @ModelAttribute("products") Products products){
        productsService.insertProduct(products);
        return "redirect:" + request.getHeader("referer");
    }
 
    @PostMapping(value = "/variants/insert") // Thêm mới biến thể cho sản phẩm
    public String insertVariants(HttpServletRequest request, @ModelAttribute("product_variants") Product_Variants productVariant){
        productVariant.setName("Color");
        productVariantService.insertVariant(productVariant);
        return "redirect:" + request.getHeader("referer");
    } 
 
    @PostMapping(value = "/variants/attributes/insert") // Thêm mới thuộc tính cho biến thể
    public String insertAttributes(HttpServletRequest request, @ModelAttribute("product_attributes") Product_Attributes productAttribute){
        productAttributeService.insertAttributes(productAttribute);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/update")
    public String updateProduct(HttpServletRequest request, @ModelAttribute("products") Products products){
        productsService.updateProduct(products);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{productID}")
    public String deleteProduct (HttpServletRequest request, @PathVariable("productID") int productID){
        if (productsService.getProductByID(productID).isPresent()){
            productsService.deleteProduct(productID);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Product not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }
    
    @PostMapping(value = "/variants/delete/{variantID}")
    public String deleteVariants (HttpServletRequest request, @PathVariable("variantID") int variantID){
        if (productVariantService.getByVariantID(variantID).isPresent()){
        	productVariantService.deteleVariant(variantID);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Record not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }
    
    @PostMapping(value = "/variants/attributes/delete/{attributeID}")
    public String deleteAttributes (HttpServletRequest request, @PathVariable("attributeID") int attributeID){
        if (productAttributeService.getByAttributeID(attributeID).isPresent()){
            productAttributeService.deleteAttribute(attributeID);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Record not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }
}
