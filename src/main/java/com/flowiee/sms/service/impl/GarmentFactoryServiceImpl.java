package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.GarmentFactory;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.repository.GarmentFactoryRepository;
import com.flowiee.sms.service.GarmentFactoryService;

import com.flowiee.sms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarmentFactoryServiceImpl implements GarmentFactoryService {
    @Autowired
    private GarmentFactoryRepository garmentFactoryRepo;

    @Override
    public List<GarmentFactory> findAll() {
        return garmentFactoryRepo.findAll();
    }

    @Override
    public GarmentFactory findById(Integer entityId) {
        return garmentFactoryRepo.findById(entityId).get();
    }

    @Override
    public GarmentFactory save(GarmentFactory entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        return garmentFactoryRepo.save(entity);
    }

    @Override
    public GarmentFactory update(GarmentFactory entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return garmentFactoryRepo.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        GarmentFactory garmentFactory = this.findById(entityId);
        if (garmentFactory == null) {
            throw new BadRequestException();
        }
        garmentFactoryRepo.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }
}