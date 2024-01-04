package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification> {
    List<Notification> findAll();

    List<Notification> findAllByReceiveId(Integer accountId);

    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);
}