package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductVariantTempDTO extends ProductVariantTemp implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer ticketImportId;
    private Integer ticketExportId;
    private Integer productVariantId;
    private String productVariantName;

    public static ProductVariantTempDTO convertToDTO(ProductVariantTemp inputEntity) {
        ProductVariantTempDTO outputDTO = new ProductVariantTempDTO();
        outputDTO.setId(inputEntity.getId());
        outputDTO.setProductVariant(inputEntity.getProductVariant());
        outputDTO.setTicketImport(inputEntity.getTicketImport());
        outputDTO.setTicketExport(inputEntity.getTicketExport());
        outputDTO.setPurchasePrice(inputEntity.getPurchasePrice());
        outputDTO.setQuantity(inputEntity.getQuantity());
        outputDTO.setNote(inputEntity.getNote());
        outputDTO.setProductVariantId(inputEntity.getProductVariant().getId());
        outputDTO.setProductVariantName(inputEntity.getProductVariant().getVariantName());
        if (inputEntity.getTicketImport() != null) {
            outputDTO.setTicketImportId(inputEntity.getTicketImport().getId());
        }
        if (inputEntity.getTicketExport() != null) {
            outputDTO.setTicketExportId(inputEntity.getTicketExport().getId());
        }
        return outputDTO;
    }

    public static List<ProductVariantTempDTO> convertToDTOs(List<ProductVariantTemp> inputEntities) {
        List<ProductVariantTempDTO> outputDTOs = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(inputEntities)) {
            for (ProductVariantTemp dto : inputEntities) {
                outputDTOs.add(convertToDTO(dto));
            }
        }
        return outputDTOs;
    }
}