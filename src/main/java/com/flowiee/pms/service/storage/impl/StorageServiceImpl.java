package com.flowiee.pms.service.storage.impl;

import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.StorageItems;
import com.flowiee.pms.model.dto.StorageDTO;
import com.flowiee.pms.repository.storage.StorageRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.storage.StorageService;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.LogType;
import com.flowiee.pms.utils.converter.StorageConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StorageServiceImpl extends BaseService implements StorageService {
    private static final String mainObjectName = "Storage";
    private final StorageRepository storageRepository;

    @Autowired
    public StorageServiceImpl(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Override
    public List<StorageDTO> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<StorageDTO> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageNum >= 0 && pageSize >= 0) {
            pageable = PageRequest.of(pageNum, pageSize);
        }
        Page<Storage> storages = storageRepository.findAll(pageable);
        return new PageImpl<>(StorageDTO.convertToDTOs(storages.getContent()), pageable, storages.getTotalElements());
    }

    @Override
    public Page<StorageItems> findStorageItems(int pageSize, int pageNum, Integer storageId, String searchText) {
        Pageable pageable = Pageable.unpaged();
        if (pageNum >= 0 && pageSize >= 0) {
            pageable = PageRequest.of(pageNum, pageSize);
        }
        Page<Object[]> storageItemsRawData = storageRepository.findAllItems(searchText, storageId, pageable);
        List<StorageItems> storageItems = new ArrayList<>();
        for (Object[] object : storageItemsRawData) {
            StorageItems s = StorageItems.builder()
                    .storageId(storageId)
                    .isProduct(object[0].toString())
                    .itemId(Integer.parseInt(object[1].toString()))
                    .itemImageSrc(object[2] != null ? object[2].toString() : null)
                    .itemName(object[3].toString())
                    .itemType(object[4] != null ? object[4].toString() : null)
                    .itemBrand(object[5] != null ? object[5].toString() : null)
                    .build();
            if (object[6] != null) s.setItemStorageQty(Integer.parseInt(String.valueOf(object[6])));
            if (object[7] != null) s.setItemSalesAvailableQty(Integer.parseInt(String.valueOf(object[7])));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            if (object[8] != null) {
                if (Objects.toString(object[8]).length() == 22) formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                if (Objects.toString(object[8]).length() == 23) formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                if (Objects.toString(object[8]).length() == 26) formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                LocalDateTime firstImportTime = LocalDateTime.parse(Objects.toString(object[8]), formatter);
                s.setFirstImportTime(firstImportTime);
            }
            if (object[9] != null) {
                if (Objects.toString(object[9]).length() == 22) formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");
                if (Objects.toString(object[9]).length() == 23) formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                if (Objects.toString(object[9]).length() == 26) formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                LocalDateTime lastImportTime = LocalDateTime.parse(Objects.toString(object[9]), formatter);
                s.setLastImportTime(lastImportTime);
            }
            storageItems.add(s);
        }
        return new PageImpl<>(storageItems, pageable, storageItemsRawData.getTotalElements());
    }

    @Override
    public Optional<StorageDTO> findById(Integer storageId) {
        Optional<Storage> storageOptional = storageRepository.findById(storageId);
        if (storageOptional.isPresent()) {
            List<StorageItems> storageItemsList = this.findStorageItems(-1, -1, storageId, null).getContent();
            StorageDTO storage = StorageDTO.convertToDTO(storageOptional.get());
            storage.setListStorageItems(storageItemsList);
            storage.setTotalItems(storageItemsList.size());
            return Optional.of(storage);
        }
        return Optional.empty();
    }

    @Override
    public StorageDTO save(StorageDTO inputStorageDTO) {
        Storage storage = StorageConvert.convertToDTO(inputStorageDTO);
        Storage storageSaved = storageRepository.save(storage);
        return StorageDTO.convertToDTO(storageSaved);
    }

    @Override
    public StorageDTO update(StorageDTO inputStorageDTO, Integer storageId) {
        if (this.findById(storageId).isEmpty()) {
            throw new BadRequestException("Storage not found");
        }
        Storage storage = StorageConvert.convertToDTO(inputStorageDTO);
        storage.setId(storageId);
        Storage storageSaved = storageRepository.save(storage);
        return StorageDTO.convertToDTO(storageSaved);
    }

    @Override
    public String delete(Integer storageId) {
        try {
            Optional<StorageDTO> storage = this.findById(storageId);
            if (storage.isEmpty()) {
                throw new BadRequestException("Storage not found");
            }
            if ("Y".equals(storage.get().getStatus())) {
                return "This storage is in use!";
            }
            storageRepository.deleteById(storageId);
            systemLogService.writeLog(MODULE.STORAGE.name(), ACTION.STG_STORAGE.name(), mainObjectName, LogType.D.name(), "Delete Storage storageId=" + storageId);
            logger.info("Delete storage success! storageId={}", storageId);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "Storage storageId=" + storageId), ex);
        }
    }
}