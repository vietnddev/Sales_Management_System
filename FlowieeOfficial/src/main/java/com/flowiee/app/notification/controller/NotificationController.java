package com.flowiee.app.notification.controller;

import com.flowiee.app.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
}
