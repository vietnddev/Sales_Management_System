package com.flowiee.pms.service.storage.impl;

import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.repository.storage.StorageRepository;
import com.flowiee.pms.service.storage.StorageService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<Storage> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<Storage> findAll(int pageNum, int pageSize) {
        Pageable pageable = Pageable.unpaged();
        if (pageNum >= 0 && pageSize >= 0) {
            pageable = PageRequest.of(pageNum, pageSize);

        }
        return storageRepository.findAll(pageable);
    }

    @Override
    public Optional<Storage> findById(Integer storageId) {
        return storageRepository.findById(storageId);
    }

    @Override
    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }

    @Override
    public Storage update(Storage storage, Integer storageId) {
        if (this.findById(storageId).isEmpty()) {
            throw new BadRequestException("Storage not found");
        }
        storage.setId(storageId);
        return storageRepository.save(storage);
    }

    @Override
    public String delete(Integer storageId) {
        try {
            if (this.findById(storageId).isEmpty()) {
                throw new BadRequestException("Storage not found");
            }
            storageRepository.deleteById(storageId);
            systemLogService.writeLog(MODULE.STORAGE.name(), ACTION.STG_STORAGE.name(), "Delete Storage storageId=" + storageId);
            logger.info("Delete storage success! storageId={}", storageId);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "Storage storageId=" + storageId), ex);
        }
    }
}