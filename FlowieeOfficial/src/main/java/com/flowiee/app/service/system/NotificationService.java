package com.flowiee.app.service.system;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification> {
    List<Notification> findAllByReceiveId(Integer accountId);

    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);
}