package com.flowiee.app.hethong.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.hethong.entity.FlowieeImport;

import java.util.List;

public interface FlowieeImportService extends BaseService<FlowieeImport> {
    List<FlowieeImport> findByAccountId(Integer accountId);
}