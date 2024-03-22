package com.flowiee.pms.service.system;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.system.FlowieeImport;

import java.util.List;

public interface ImportService extends BaseService<FlowieeImport> {
    List<FlowieeImport> findAll();

    List<FlowieeImport> findByAccountId(Integer accountId);
}