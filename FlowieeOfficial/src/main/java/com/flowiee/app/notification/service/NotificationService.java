package com.flowiee.app.notification.service;

import com.flowiee.app.notification.entity.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> findAll();
}