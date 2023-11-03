package com.flowiee.app.category;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/system/category")
public class CategoryController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModuleDanhMuc;

    @GetMapping("")
    public ModelAndView viewRootCategory() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_CATEGORY);
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("listCategory", categoryService.findRootCategory());
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/{type}")
    public ModelAndView viewSubCategory(@PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleDanhMuc.kiemTraQuyenXem()) {
            List<Category> listCategory = categoryService.findSubCategory(CategoryUtil.getCategoryType(categoryType));
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_CATEGORY);
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("listCategory", listCategory);
            modelAndView.addObject("categoryType", categoryType);
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/{type}/insert")
    public String insert(@ModelAttribute("category") Category category, @PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        category.setType(CategoryUtil.getCategoryType(categoryType));
        categoryService.save(category);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("category") Category category,
                         @PathVariable("id") Integer categoryId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (categoryId <= 0) {
            throw new BadRequestException();
        }
        categoryService.update(category, categoryId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer categoryId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        categoryService.delete(categoryId);
        return "redirect:" + request.getHeader("referer");
    }
}