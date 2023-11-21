package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Notification;
import com.flowiee.app.repository.NotificationRepository;
import com.flowiee.app.service.NotificationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> findAllByReceiveId(Integer accountId) {
        return notificationRepository.findAllByReceiveId(accountId);
    }

    @Override
    public List<Notification> findLimitByReceiveId(Integer accountId, Integer limit) {
        return notificationRepository.findLimitByReceiveId(accountId, limit);
    }

    @Override
    public Notification findById(Integer notificationId) {
        return notificationRepository.findById(notificationId).orElse(null);
    }

    @Override
    public String save(Notification notification) {
        if (notification == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        notificationRepository.save(notification);
        logger.info(NotificationServiceImpl.class.getName() + ": Insert notification " + notification.toString());
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Notification entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        notificationRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        Notification notification = this.findById(entityId);
        if (notification == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        notificationRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}