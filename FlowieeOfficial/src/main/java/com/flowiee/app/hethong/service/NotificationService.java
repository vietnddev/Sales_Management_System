package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();

    List<Notification> findByReceiveId(Integer accountId);

    Notification findById(Integer notificationId);

    String save(Notification notification);
}