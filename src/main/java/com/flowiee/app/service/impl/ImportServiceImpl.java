package com.flowiee.app.service.impl;

import com.flowiee.app.entity.FlowieeImport;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.FlowieeImportRepository;
import com.flowiee.app.service.ImportService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {
    @Autowired
    private FlowieeImportRepository flowieeImportRepository;

    @Override
    public List<FlowieeImport> findAll() {
        return flowieeImportRepository.findAll();
    }

    @Override
    public List<FlowieeImport> findByAccountId(Integer accountId) {
        return flowieeImportRepository.findByAccountId(accountId);
    }

    @Override
    public FlowieeImport findById(Integer importId) {
        return flowieeImportRepository.findById(importId).orElse(null);
    }

    @Override
    public FlowieeImport save(FlowieeImport flowieeImport) {
        if (flowieeImport == null) {
            throw new BadRequestException();
        }
        return flowieeImportRepository.save(flowieeImport);
    }

    @Override
    public FlowieeImport update(FlowieeImport entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return flowieeImportRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        FlowieeImport flowieeImport = this.findById(entityId);
        if (flowieeImport == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        flowieeImportRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}