package com.flowiee.app.system.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.system.entity.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification> {
    List<Notification> findAllByReceiveId(Integer accountId);

    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);
}