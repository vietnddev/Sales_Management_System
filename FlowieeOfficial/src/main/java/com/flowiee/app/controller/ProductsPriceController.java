package com.flowiee.app.controller;

import com.flowiee.app.services.PriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductsPriceController {
    @Autowired
    private PriceHistoryService priceHistoryService;

    @GetMapping("/sales/products/price/create")
    public String savePrice(@RequestParam("productVariantID") int productVariantID){
        System.out.println("productVariantID " + productVariantID);
        return "";
    }
}
