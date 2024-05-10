package com.flowiee.pms.service.storage;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.storage.Storage;
import org.springframework.data.domain.Page;

public interface StorageService extends BaseService<Storage> {
    Page<Storage> findAll(int pageNum, int pageSize);
}