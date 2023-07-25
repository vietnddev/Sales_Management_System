package com.flowiee.app.congviec;

import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/lich-lam-viec")
public class LichLamViecController {

    @GetMapping
    public ModelAndView findLichLamViec() {
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_CONGVIEC_LICHLAMVIEC);
        return modelAndView;
    }
}