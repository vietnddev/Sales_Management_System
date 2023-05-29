package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/san-pham/thu-vien-hinh-anh")
public class ThuVienHinhAnhController {

    @GetMapping
    public String getAllThuVienHinhAnh() {
        return PagesUtil.PAGE_UNAUTHORIZED;
    }
}
