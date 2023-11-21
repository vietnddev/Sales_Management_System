package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.FlowieeImport;
import com.flowiee.app.repository.FlowieeImportRepository;
import com.flowiee.app.service.system.FlowieeImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowieeImportImpl implements FlowieeImportService {
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
    public String save(FlowieeImport flowieeImport) {
        if (flowieeImport == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        flowieeImportRepository.save(flowieeImport);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(FlowieeImport entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        flowieeImportRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        FlowieeImport flowieeImport = this.findById(entityId);
        if (flowieeImport == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        flowieeImportRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}