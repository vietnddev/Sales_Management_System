package com.flowiee.app.category;

import com.flowiee.app.category.entity.DonViTinh;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.EndPointUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("/category")
public class CategoryController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModuleDanhMuc;

    @GetMapping("/{type}")
    public ModelAndView findAll(@PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView();
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            switch (categoryType) {
                case "unit":
                    categoryType = "UNIT";
                    break;
                case "fabric-type":
                    categoryType = "FABRICTYPE";
                    break;
                case "pay-method":
                    categoryType = "PAYMETHOD";
                    break;
                case "sales-channel":
                    categoryType = "SALESCHANNEL";
                    break;
                case "size":
                    categoryType = "SIZE";
                    break;
                case "color":
                    categoryType = "COLOR";
                    break;
                case "product-type":
                    categoryType = "PRODUCTTYPE";
                    break;
                case "document-type":
                    categoryType = "DOCUMENTTYPE";
                    break;
                case "order-status":
                    categoryType = "ORDERSTATUS";
                    break;
            }
            List<Category> listCategory = categoryService.findByType(categoryType);

            modelAndView.addObject(PagesUtil.PAGE_DANHMUC_DONVITINH);
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("listCategory", listCategory);
            modelAndView.addObject("categoryType", categoryType);
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
        } else {
            modelAndView.addObject(PagesUtil.PAGE_UNAUTHORIZED);
        }
        return modelAndView;
    }

    @PostMapping("/{type}/insert")
    public String insert(@ModelAttribute("category") Category category, @PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        switch (categoryType) {
            case "unit":
                categoryType = "UNIT";
                break;
            case "fabric-type":
                categoryType = "FABRICTYPE";
                break;
            case "pay-method":
                categoryType = "PAYMETHOD";
                break;
            case "sales-channel":
                categoryType = "SALESCHANNEL";
                break;
            case "size":
                categoryType = "SIZE";
                break;
            case "color":
                categoryType = "COLOR";
                break;
            case "product-type":
                categoryType = "PRODUCTTYPE";
                break;
            case "document-type":
                categoryType = "DOCUMENTTYPE";
                break;
            case "order-status":
                categoryType = "ORDERSTATUS";
                break;
        }
        category.setType(categoryType);
        categoryService.save(category);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("donViTinh") DonViTinh donViTinh,
                         @PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        donViTinhService.update(donViTinh, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        donViTinhService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}