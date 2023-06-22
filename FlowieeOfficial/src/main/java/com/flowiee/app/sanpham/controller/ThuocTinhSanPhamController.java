package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;
import com.flowiee.app.sanpham.services.ThuocTinhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/san-pham/attribute/")
public class ThuocTinhSanPhamController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ThuocTinhSanPhamService thuocTinhSanPhamService;

    //Thêm mới thuộc tính
    @PostMapping(value = "/insert")
    public String insertAttributes(HttpServletRequest request, @ModelAttribute("thuocTinhSanPham") ThuocTinhSanPham thuocTinhSanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        thuocTinhSanPhamService.saveAttribute(thuocTinhSanPham);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/update/{id}")
    public String updateAttribute(@ModelAttribute("thuocTinhSanPham") ThuocTinhSanPham attribute,
                                  HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        attribute.setId(id);
        thuocTinhSanPhamService.saveAttribute(attribute);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/delete/{id}")
    public String deleteAttribute(@ModelAttribute("attribute") ThuocTinhSanPham attribute,
                                  HttpServletRequest request, @PathVariable("id") int attributeID) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (thuocTinhSanPhamService.findById(attributeID) != null) {
            thuocTinhSanPhamService.deleteAttribute(attributeID);
            return "redirect:" + request.getHeader("referer");
        } else {
            throw new NotFoundException();
        }
    }
}