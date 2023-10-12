package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.FlowieeImport;

import java.util.List;

public interface FlowieeImportService {
    List<FlowieeImport> findAll();

    List<FlowieeImport> findByAccountId(Integer accountId);

    FlowieeImport findById(Integer importId);

    String save(FlowieeImport flowieeImport);
}