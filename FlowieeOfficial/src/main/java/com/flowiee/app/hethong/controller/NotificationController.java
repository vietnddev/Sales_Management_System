package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/he-thong/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getAllNotification() {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        return PagesUtil.PAGE_UNAUTHORIZED;
    }
}
