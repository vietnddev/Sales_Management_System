package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.GarmentFactory;
import com.flowiee.app.repository.GarmentFactoryRepository;
import com.flowiee.app.service.GarmentFactoryService;

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
    public String save(GarmentFactory entity) {
        if (entity == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        garmentFactoryRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(GarmentFactory entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        garmentFactoryRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        GarmentFactory garmentFactory = this.findById(entityId);
        if (garmentFactory == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        garmentFactoryRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}