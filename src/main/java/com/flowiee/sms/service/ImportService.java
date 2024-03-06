package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.FlowieeImport;

import java.util.List;

public interface ImportService extends BaseService<FlowieeImport> {
    List<FlowieeImport> findAll();

    List<FlowieeImport> findByAccountId(Integer accountId);
}