package com.flowiee.app.controller.sales;

import com.flowiee.app.model.admin.Log;
import com.flowiee.app.model.sales.Product;
import com.flowiee.app.services.CategoryService;
import com.flowiee.app.services.LogService;
import com.flowiee.app.services.ProductService;
import com.flowiee.app.utils.Crawlers2;
import com.flowiee.app.utils.DatetimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        modelMap.addAttribute("product", new Product());
        modelMap.addAttribute("listTypeProduct", categoryService.getListCategory("typeProduct"));
        modelMap.addAttribute("listColorProduct", categoryService.getListCategory("colorProduct"));
        modelMap.addAttribute("listSizeProduct", categoryService.getListCategory("sizeProduct"));
        return "pages/sales/detailproduct";
    }

    @PostMapping(value = "/insert")
    public String InsertProruct(@ModelAttribute("product") Product product) {
        productService.insertProruct(product);
        return "redirect:/sales/product";
    }

    @PostMapping(value = "/update", params = "update")
    public String UpdateProruct(@ModelAttribute("product") Product product, HttpServletRequest request) {
        productService.updateProruct(product);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete-{id}")
    public String DeleteProruct(@PathVariable int id) {
        productService.deleteProruct(id);
        return "redirect:/sales/product";
    }

    @PostMapping(value = "/upload2")
    public String uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Path path;
        String rootDir = request.getSession().getServletContext().getRealPath("/");
        if (file != null && !file.isEmpty()) {
            path = Paths.get("E:\\Test\\abc.png");
            file.transferTo(new File(path.toString()));
        }
        System.out.println("Ok upload");
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // Kiểm tra nếu file không được chọn
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn file để upload");
            return "redirect://sales/product/upload";
        }

        try {
            // Lưu file vào thư mục tạm thời
            byte[] bytes = file.getBytes();
            Path path = Paths.get("E:\\Test\\" + file.getOriginalFilename());
            Files.write(path, bytes);

            // Lưu file vào database
//            Product product = new Product();
//            product.setImage(bytes);
//            productRepository.save(product);

            redirectAttributes.addFlashAttribute("message",
                    "Upload file thành công '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return "redirect:" + request.getHeader("referer");
        }
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
