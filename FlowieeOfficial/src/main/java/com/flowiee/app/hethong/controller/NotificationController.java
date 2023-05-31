package com.flowiee.app.hethong.controller;

import com.flowiee.app.hethong.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
}
