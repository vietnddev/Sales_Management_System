package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.hethong.entity.Notification;
import com.flowiee.app.hethong.repository.NotificationRepository;
import com.flowiee.app.hethong.service.NotificationService;
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
        notificationRepository.save(notification);
        logger.info(NotificationServiceImpl.class.getName() + ": Insert notification " + notification.toString());
        return "OK";
    }

    @Override
    public String update(Notification entity, Integer entityId) {
        return null;
    }

    @Override
    public String delete(Integer entityId) {
        return null;
    }
}