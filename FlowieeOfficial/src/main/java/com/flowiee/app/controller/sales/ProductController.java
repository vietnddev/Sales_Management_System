package com.flowiee.app.controller.sales;

import com.flowiee.app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/sales/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping(value = "")
    public String getAllProducts(ModelMap modelMap){
        modelMap.addAttribute("listProduct", productService.getAll());
        return "pages/sales/product";
    }
}
