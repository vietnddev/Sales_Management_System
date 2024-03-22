package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.Notification;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.system.NotificationRepository;
import com.flowiee.pms.service.system.NotificationService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> findAllByReceiveId(Integer pageSize, Integer pageNum, Integer totalRecord, Integer accountId) {
        if (totalRecord != null) {
            return notificationRepository.findLimitByReceiveId(accountId, totalRecord);
        }
        return notificationRepository.findAllByReceiveId(accountId);
    }

    @Override
    public List<Notification> findLimitByReceiveId(Integer accountId, Integer limit) {
        return notificationRepository.findLimitByReceiveId(accountId, limit);
    }

    @Override
    public Optional<Notification> findById(Integer notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Override
    public Notification save(Notification notification) {
        if (notification == null) {
            throw new BadRequestException();
        }
        Notification notificationSaved = notificationRepository.save(notification);
        logger.info(NotificationServiceImpl.class.getName() + ": Insert notification " + notification.toString());
        return notificationSaved;
    }

    @Override
    public Notification update(Notification entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return notificationRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        Optional<Notification> notification = this.findById(entityId);
        if (notification.isEmpty()) {
            throw new BadRequestException();
        }
        notificationRepository.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }
}