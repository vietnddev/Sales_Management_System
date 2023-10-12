package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.hethong.entity.FlowieeImport;
import com.flowiee.app.hethong.repository.FlowieeImportRepository;
import com.flowiee.app.hethong.service.FlowieeImportService;
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
        flowieeImportRepository.save(flowieeImport);
        return "OK";
    }
}