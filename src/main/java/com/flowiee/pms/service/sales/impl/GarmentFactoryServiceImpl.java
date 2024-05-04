package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.GarmentFactory;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.GarmentFactoryRepository;
import com.flowiee.pms.service.sales.GarmentFactoryService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarmentFactoryServiceImpl implements GarmentFactoryService {
    @Autowired
    private GarmentFactoryRepository garmentFactoryRepo;

    @Override
    public List<GarmentFactory> findAll() {
        return garmentFactoryRepo.findAll();
    }

    @Override
    public Optional<GarmentFactory> findById(Integer entityId) {
        return garmentFactoryRepo.findById(entityId);
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
        if (this.findById(entityId).isEmpty()) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return garmentFactoryRepo.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (this.findById(entityId).isEmpty()) {
            throw new BadRequestException();
        }
        garmentFactoryRepo.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }
}