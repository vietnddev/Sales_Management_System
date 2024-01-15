package com.flowiee.app.service.impl;

import com.flowiee.app.entity.GarmentFactory;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.GarmentFactoryRepository;
import com.flowiee.app.service.GarmentFactoryService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarmentFactoryServiceImpl implements GarmentFactoryService {
    @Autowired
    private GarmentFactoryRepository garmentFactoryRepository;

    @Override
    public List<GarmentFactory> findAll() {
        return garmentFactoryRepository.findAll();
    }

    @Override
    public GarmentFactory findById(Integer entityId) {
        return garmentFactoryRepository.findById(entityId).get();
    }

    @Override
    public GarmentFactory save(GarmentFactory entity) {
        if (entity == null) {
            throw new BadRequestException();
        }
        return garmentFactoryRepository.save(entity);
    }

    @Override
    public GarmentFactory update(GarmentFactory entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return garmentFactoryRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        GarmentFactory garmentFactory = this.findById(entityId);
        if (garmentFactory == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        garmentFactoryRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}