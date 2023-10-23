package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.danhmuc.entity.FabricType;
import com.flowiee.app.danhmuc.repository.FabricTypeRepository;
import com.flowiee.app.danhmuc.service.FabricTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricTypeServiceImpl implements FabricTypeService {
    @Autowired
    private FabricTypeRepository fabricTypeRepository;

    @Override
    public List<FabricType> findAll() {
        return fabricTypeRepository.findAll();
    }

    @Override
    public FabricType findById(Integer entityId) {
        return fabricTypeRepository.findById(entityId).orElse(null);
    }

    @Override
    public String save(FabricType entity) {
        fabricTypeRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(FabricType entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        fabricTypeRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        FabricType fabricType = this.findById(entityId);
        fabricTypeRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}