package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.FlowieeImport;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.system.AppImportRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.ImportService;

import com.flowiee.pms.utils.constants.MessageCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImportServiceImpl extends BaseService implements ImportService {
    private final AppImportRepository appImportRepository;

    public ImportServiceImpl(AppImportRepository appImportRepository) {
        this.appImportRepository = appImportRepository;
    }

    @Override
    public List<FlowieeImport> findAll() {
        return appImportRepository.findAll();
    }

    @Override
    public List<FlowieeImport> findByAccountId(Integer accountId) {
        return appImportRepository.findByAccountId(accountId);
    }

    @Override
    public Optional<FlowieeImport> findById(Integer importId) {
        return appImportRepository.findById(importId);
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
        Optional<FlowieeImport> fImport = this.findById(entityId);
        if (fImport.isEmpty()) {
            throw new BadRequestException();
        }
        appImportRepository.deleteById(entityId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}