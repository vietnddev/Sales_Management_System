package com.flowiee.app.service.system;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.system.FlowieeImport;

import java.util.List;

public interface FlowieeImportService extends BaseService<FlowieeImport> {
    List<FlowieeImport> findByAccountId(Integer accountId);
}