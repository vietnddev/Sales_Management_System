package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Transient;
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
    //Popup's field get import/export storage of product
    private String staff;
    private String changeQty;
    private String branchName;

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
        if (outputDTO.getProductVariant() != null) {
            outputDTO.setProductVariantId(outputDTO.getProductVariant().getId());
            outputDTO.setProductVariantName(outputDTO.getProductVariant().getVariantName());
        }
        if (outputDTO.getTicketImport() != null) {
            outputDTO.setTicketImportId(inputEntity.getTicketImport().getId());
        }
        if (outputDTO.getTicketExport() != null) {
            outputDTO.setTicketExportId(inputEntity.getTicketExport().getId());
        }
        outputDTO.setStorageQty(inputEntity.getStorageQty());
        outputDTO.setCreatedAt(inputEntity.getCreatedAt());
        outputDTO.setAction(inputEntity.getAction());
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