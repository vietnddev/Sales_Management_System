package com.flowiee.pms.service.system;

import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.entity.system.Notification;

import java.util.List;

public interface NotificationService extends BaseCurd<Notification> {
    List<Notification> findAllByReceiveId(Integer pageSize, Integer pageNum, Integer totalRecord, Integer accountId);

    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);
}