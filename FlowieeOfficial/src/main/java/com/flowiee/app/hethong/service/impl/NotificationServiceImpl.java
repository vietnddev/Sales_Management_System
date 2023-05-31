package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.hethong.entity.Notification;
import com.flowiee.app.hethong.repository.NotificationRepository;
import com.flowiee.app.hethong.service.NotificationService;
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
