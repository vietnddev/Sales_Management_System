package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();

    List<Notification> findAllByReceiveId(Integer accountId);

    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);

    Notification findById(Integer notificationId);

    String save(Notification notification);
}