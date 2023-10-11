package com.flowiee.app.khotailieu.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleKhoTaiLieu;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.khotailieu.entity.DocField;
import com.flowiee.app.khotailieu.service.DocFieldService;
import com.flowiee.app.hethong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/kho-tai-lieu/docfield")
public class DocFieldController {
    @Autowired
    private DocFieldService docFieldService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModuleKhoTaiLieu kiemTraQuyenModuleKhoTaiLieu;

    @PostMapping("/insert")
    public String create(DocField docField, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleThemMoiDocument()) {
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
        if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleCapNhatDocument()) {
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
        if (kiemTraQuyenModuleKhoTaiLieu.kiemTraRoleXoaDocument()) {
            docFieldService.delete(id);
            return "redirect:" + request.getHeader("referer");
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}