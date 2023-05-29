package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/he-thong/nhom-quyen")
public class NhomQuyenController {

    @GetMapping
    public String getAllNhomQuyen() {
        return PagesUtil.PAGE_UNAUTHORIZED;
    }
}
