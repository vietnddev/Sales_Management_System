package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.FlowieeImport;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.repository.AppImportRepository;
import com.flowiee.sms.service.ImportService;

import com.flowiee.sms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {
    @Autowired
    private AppImportRepository appImportRepository;

    @Override
    public List<FlowieeImport> findAll() {
        return appImportRepository.findAll();
    }

    @Override
    public List<FlowieeImport> findByAccountId(Integer accountId) {
        return appImportRepository.findByAccountId(accountId);
    }

    @Override
    public FlowieeImport findById(Integer importId) {
        return appImportRepository.findById(importId).orElse(null);
    }

    @Override
    public FlowieeImport save(FlowieeImport flowieeImport) {
        if (flowieeImport == null) {
            throw new BadRequestException();
        }
        return appImportRepository.save(flowieeImport);
    }

    @Override
    public FlowieeImport update(FlowieeImport entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            throw new BadRequestException();
        }
        entity.setId(entityId);
        return appImportRepository.save(entity);
    }

    @Override
    public String delete(Integer entityId) {
        FlowieeImport flowieeImport = this.findById(entityId);
        if (flowieeImport == null) {
            throw new BadRequestException();
        }
        appImportRepository.deleteById(entityId);
        return MessageUtils.DELETE_SUCCESS;
    }
}