package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.model.StorageItems;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StorageDTO extends Storage implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	private Integer totalItems;
    private BigDecimal totalInventoryValue;
    private List<TicketImportDTO> listTicketImportDTO;
    private List<ProductVariantDTO> listProductVariantDTO;
    private List<MaterialDTO> listMaterialDTO;
    private List<StorageItems> listStorageItems;

    public static StorageDTO convertToDTO(Storage inputEntity) {
        if (inputEntity == null) {
            return null;
        }
        StorageDTO outputDTO = new StorageDTO();
        outputDTO.setId(inputEntity.getId());
        outputDTO.setName(inputEntity.getName());
        outputDTO.setCode(inputEntity.getCode());
        outputDTO.setLocation(inputEntity.getLocation());
        outputDTO.setArea(inputEntity.getArea());
        outputDTO.setHoldableQty(inputEntity.getHoldableQty());
        outputDTO.setHoldWarningPercent(inputEntity.getHoldWarningPercent());
        outputDTO.setDescription(inputEntity.getDescription());
        outputDTO.setStatus(inputEntity.getStatus());
        return outputDTO;
    }

    public static List<StorageDTO> convertToDTOs(List<Storage> inputEntities) {
        List<StorageDTO> outputDTOs = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(inputEntities)) {
            for (Storage s : inputEntities) {
                outputDTOs.add(StorageDTO.convertToDTO(s));
            }
        }
        return outputDTOs;
    }
}