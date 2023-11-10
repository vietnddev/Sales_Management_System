package com.flowiee.app.controller.storage;

import com.flowiee.app.config.ValidateModuleStorage;
import com.flowiee.app.entity.storage.DocField;
import com.flowiee.app.service.storage.DocFieldService;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/kho-tai-lieu/docfield")
public class DocFieldController extends BaseController {
    @Autowired
    private DocFieldService docFieldService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ValidateModuleStorage validateModuleStorage;

    @PostMapping("/insert")
    public String create(DocField docField, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (validateModuleStorage.update()) {
            docField.setTrangThai(false);
            docFieldService.save(docField);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping(value = "/update/{id}", params = "update")
    public String update(HttpServletRequest request,
                         @ModelAttribute("docField") DocField docField,
                         @PathVariable("id") Integer docFieldId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (validateModuleStorage.update()) {
            System.out.println(docField.toString());
            docFieldService.update(docField, docFieldId);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        String username = FlowieeUtil.ACCOUNT_USERNAME;
        if (username == null || username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (docFieldService.findById(id) == null){
            throw new BadRequestException();
        }
        if (validateModuleStorage.delete()) {
            docFieldService.delete(id);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}