package com.flowiee.app.products.controller;

import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.products.entity.BienTheSanPham;
import com.flowiee.app.products.entity.GiaSanPham;
import com.flowiee.app.products.entity.ThuocTinhSanPham;
import com.flowiee.app.products.services.BienTheSanPhamService;
import com.flowiee.app.products.services.GiaSanPhamService;
import com.flowiee.app.products.services.ThuocTinhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/san-pham")
public class SanPhamBienTheController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ThuocTinhSanPhamService thuocTinhSanPhamService;
    @Autowired
    private GiaSanPhamService giaSanPhamService;

    @GetMapping(value = "{productID}/variants/{productVariantID}") // Show trang chi tiết của biến thể
    public String getDetailProductVariant(ModelMap modelMap, @PathVariable("productVariantID") int productVariantID) {
        /* Show trang chi tiết của biến thể
         * */
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("product_variants", new BienTheSanPham());
            modelMap.addAttribute("product_attributes", new ThuocTinhSanPham());
            modelMap.addAttribute("price_history", new GiaSanPham());
            modelMap.addAttribute("listAttributes", thuocTinhSanPhamService.getAllAttributes(productVariantID));
            modelMap.addAttribute("productVariantID", productVariantID);
            //Lấy danh sách hình ảnh của biến thể
            //modelMap.addAttribute("listFiles", fileService.getFilesByProductVariant(productVariantID));
            //
            modelMap.addAttribute("listPrices", giaSanPhamService.getListPriceByPVariantID(productVariantID));
            return "pages/sales/product_variant";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @PostMapping(value = "/bien-the/insert") // Thêm mới biến thể cho sản phẩm
    public String insertVariants(HttpServletRequest request, RedirectAttributes redirectAttributes,
                                 @ModelAttribute("bienTheSanPham") BienTheSanPham bienTheSanPham) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            bienTheSanPham.setLoaiBienThe("Color");
            bienTheSanPhamService.insertVariant(bienTheSanPham);

            //Khởi tạo giá default của giá bán
            //priceHistoryService.save(new GiaSanPham(productVariant.getProductVariantID(), 199));

            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/bien-the/delete/{variantID}") //delete biến thể
    public String deleteVariants(HttpServletRequest request, @PathVariable("variantID") int variantID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (bienTheSanPhamService.getByVariantID(variantID).isPresent()) {
                bienTheSanPhamService.deteleVariant(variantID);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Record not found!");
            }
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }
}
