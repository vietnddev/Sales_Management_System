package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.Voucher;
import com.flowiee.app.sanpham.entity.VoucherDetail;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.flowiee.app.security.authorization.KiemTraQuyenModuleSanPham;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/san-pham/voucher")
public class VoucherController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping
    public ModelAndView showVoucherPage() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            Map<String, String> voucherType = new HashMap<>();
            voucherType.put("BOTH", "Bao gồm chữ và số");
            voucherType.put("NUMBER", "Chỉ số");
            voucherType.put("TEXT", "Chỉ chữ");

            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM_VOUCHER);
            modelAndView.addObject("listVoucher", voucherService.findAll());
            modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelAndView.addObject("listVoucherType", voucherType);
            modelAndView.addObject("voucher", new Voucher());
            modelAndView.addObject("voucherDetail", new VoucherDetail());
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/insert")
    public ModelAndView insertVoucher(@ModelAttribute("voucher") Voucher voucher,
                                      HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemVoucher()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                voucher.setStartTime(dateFormat.parse(request.getParameter("startTime_")));
                voucher.setEndTime(dateFormat.parse(request.getParameter("endTime_")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<Integer> listBienTheSP = new ArrayList<>();
            String[] pbienTheSP = request.getParameterValues("bienTheSanPhamId");
            if (pbienTheSP != null) {
                for (String id : pbienTheSP) {
                    listBienTheSP.add(Integer.parseInt(id));
                }
            }
            if (listBienTheSP.size() > 0) {
                voucherService.save(voucher, listBienTheSP);
            }
            return new ModelAndView("redirect:/san-pham/voucher");
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}