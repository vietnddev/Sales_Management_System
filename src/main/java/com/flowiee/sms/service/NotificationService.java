package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.Notification;

import java.util.List;

public interface NotificationService extends BaseService<Notification> {
    List<Notification> findAll();

    List<Notification> findAllByReceiveId(Integer pageSize, Integer pageNum, Integer totalRecord, Integer accountId);

    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);
}