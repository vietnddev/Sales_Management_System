package com.flowiee.app.controller.sales;

import com.flowiee.app.model.admin.Log;
import com.flowiee.app.model.sales.Product;
import com.flowiee.app.services.CategoryService;
import com.flowiee.app.services.LogService;
import com.flowiee.app.services.ProductService;
import com.flowiee.app.utils.Crawlers2;
import com.flowiee.app.utils.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/sales/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LogService logService;

    @GetMapping(value = "")
    public String GetAllProducts(ModelMap modelMap){
        logService.insertLog(new Log("User - update later", "Truy cập chức năng", "Xem danh sách sản phẩm", "URL - update later", DatetimeUtil.now(), "192.168.x.x"));

        modelMap.addAttribute("listProduct", productService.getAll());
        modelMap.addAttribute("product", new Product());
        modelMap.addAttribute("listTypeProduct", categoryService.getListCategory("typeProduct"));
        modelMap.addAttribute("listColorProduct", categoryService.getListCategory("colorProduct"));
        modelMap.addAttribute("listSizeProduct", categoryService.getListCategory("sizeProduct"));
        return "pages/sales/product";
    }

    @GetMapping(value = "/detail-{id}")
    public String GetDetailByID(ModelMap modelMap, @PathVariable int id){
        //List<Product> list = productService.getByID(id);
        modelMap.addAttribute("detailProduct", productService.getByID(id));
        return "pages/sales/detailproduct";
    }

    @PostMapping(value = "/insert")
    public String InsertProruct(@ModelAttribute("product") Product product) {
        productService.insertProruct(product);
        return "redirect:/sales/product";
    }

    @PostMapping(value = "/delete-{id}")
    public String DeleteProruct(@PathVariable int id) {
        productService.deleteProruct(id);
        return "redirect:/sales/product";
    }

    @GetMapping(value = "/sync")
    @ResponseBody
    public void SyncListProduct(ModelMap modelMap){
        Crawlers2 crawlers = new Crawlers2();
        System.out.println(crawlers.getListCode());
        System.out.println(crawlers.getListName());
        System.out.println(crawlers.getListPrice());
        System.out.println(crawlers.getListImage());
        //return "pages/sales/product";
    }
}
