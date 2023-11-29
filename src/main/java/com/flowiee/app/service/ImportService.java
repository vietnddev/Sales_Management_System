package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.FlowieeImport;

import java.util.List;

public interface ImportService extends BaseService<FlowieeImport> {
    List<FlowieeImport> findByAccountId(Integer accountId);
}