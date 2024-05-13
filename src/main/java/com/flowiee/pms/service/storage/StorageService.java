package com.flowiee.pms.service.storage;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.model.StorageItems;
import com.flowiee.pms.model.dto.StorageDTO;
import org.springframework.data.domain.Page;

public interface StorageService extends CrudService<StorageDTO> {
    Page<StorageDTO> findAll(int pageSize, int pageNum);

    Page<StorageItems> findStorageItems(int pageSize, int pageNum, Integer storageId, String searchText);
}