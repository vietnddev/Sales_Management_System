package com.flowiee.app.system.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.system.entity.FlowieeImport;

import java.util.List;

public interface FlowieeImportService extends BaseService<FlowieeImport> {
    List<FlowieeImport> findByAccountId(Integer accountId);
}