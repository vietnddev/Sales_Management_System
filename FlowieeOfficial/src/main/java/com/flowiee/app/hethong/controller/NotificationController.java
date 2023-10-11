package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.entity.Notification;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/he-thong/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ModelAndView getAllNotification() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_NOTIFICATION);
        modelAndView.addObject("notification", new Notification());
        modelAndView.addObject("listNotification", notificationService.findAll());
        return modelAndView;
    }
}