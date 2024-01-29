package com.flowiee.app.service.impl;

import com.flowiee.app.entity.MaterialTemp;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.MaterialTempRepository;
import com.flowiee.app.service.MaterialTempService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialTempServiceImpl implements MaterialTempService {
    @Autowired
    private MaterialTempRepository materialTempRepo;

    @Override
    public List<MaterialTemp> findAll() {
        return materialTempRepo.findAll();
    }

    @Override
    public MaterialTemp findById(Integer entityId) {
        return materialTempRepo.findById(entityId).get();
    }

    @Override
    public MaterialTemp save(MaterialTemp entity) {
        return materialTempRepo.save(entity);
    }

    @Override
    public MaterialTemp update(MaterialTemp entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return materialTempRepo.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        materialTempRepo.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<MaterialTemp> findByImportId(Integer importId) {
        List<MaterialTemp> listData = new ArrayList<>();
        if (importId != null && importId > 0) {
            listData = materialTempRepo.findByImportId(importId);
        }
        return listData;
    }

	@Override
	public MaterialTemp findMaterialInGoodsImport(Integer importId, Integer materialId) {
		return materialTempRepo.findMaterialInGoodsImport(importId, materialId);
	}
}