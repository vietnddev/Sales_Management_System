package com.flowiee.pms.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class StorageItems {
    private Integer storageId;
    private Integer itemId;
    private String itemImageSrc;
    private String itemName;
    private String itemType;
    private String itemBrand;
    private Integer itemSalesAvailableQty;
    private Integer itemStorageQty;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastImportTime;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime firstImportTime;
    private String isProduct;
}