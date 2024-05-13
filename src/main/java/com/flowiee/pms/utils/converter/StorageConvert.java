package com.flowiee.pms.utils.converter;

import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.model.dto.StorageDTO;

public class StorageConvert {
    public static Storage convertToDTO(StorageDTO inputDTO) {
        Storage storage = new Storage();
        storage.setId(inputDTO.getId());
        storage.setName(inputDTO.getName());
        storage.setCode(inputDTO.getCode());
        storage.setLocation(inputDTO.getLocation());
        storage.setArea(inputDTO.getArea());
        storage.setHoldableQty(inputDTO.getHoldableQty());
        storage.setHoldWarningPercent(inputDTO.getHoldWarningPercent());
        storage.setDescription(inputDTO.getDescription());
        storage.setStatus(inputDTO.getStatus());
        return storage;
    }
}