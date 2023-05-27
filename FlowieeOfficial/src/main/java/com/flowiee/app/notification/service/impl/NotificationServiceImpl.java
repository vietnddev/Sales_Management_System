package com.flowiee.app.notification.service.impl;

import com.flowiee.app.notification.entity.Notification;
import com.flowiee.app.notification.repository.NotificationRepository;
import com.flowiee.app.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }
}
