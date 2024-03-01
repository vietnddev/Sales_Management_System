package com.flowiee.app.model.dto;

import com.flowiee.app.entity.MaterialTemp;
import com.flowiee.app.entity.ProductVariantTemp;
import com.flowiee.app.entity.TicketImport;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TicketImportDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private Integer supplierId;
    private String supplierName;
    private Integer paymentMethodId;
    private String paymentMethodName;
    private String importer;
    private Date importTime;
    private String note;
    private String status;
    private List<ProductVariantDTO> listProductDTO;
    private List<ProductVariantTemp> listProductVariantTemp;
    private List<MaterialDTO> listMaterialDTO;
    private List<MaterialTemp> listMaterialTemp;

    public static TicketImportDTO fromTicketImport(TicketImport ticketImport) {
        TicketImportDTO dto = new TicketImportDTO();
        dto.setId(ticketImport.getId());
        dto.setTitle(ticketImport.getTitle());
        if (ticketImport.getSupplier() != null) {
            dto.setSupplierId(ticketImport.getSupplier().getId());
            dto.setSupplierName(ticketImport.getSupplier().getName());
        }
        if (ticketImport.getPaymentMethod() != null) {
            dto.setPaymentMethodId(ticketImport.getPaymentMethod().getId());
            dto.setPaymentMethodName(ticketImport.getPaymentMethod().getName());
        }
        dto.setImporter(ticketImport.getImporter());
        dto.setImportTime(ticketImport.getImportTime());
        dto.setNote(ticketImport.getNote());
        dto.setStatus(ticketImport.getStatus());
        dto.setListProductDTO(ProductVariantDTO.fromProductVariants(ticketImport.getListProductVariants()));
        dto.setListProductVariantTemp(ticketImport.getListProductVariantTemps());
        dto.setListMaterialDTO(MaterialDTO.fromMaterials(ticketImport.getListMaterials()));
        dto.setListMaterialTemp(ticketImport.getListMaterialTemps());
        return dto;
    }

    public static List<TicketImportDTO> fromTicketImports(List<TicketImport> ticketImports) {
        List<TicketImportDTO> list = new ArrayList<>();
        for (TicketImport t : ticketImports) {
            list.add(TicketImportDTO.fromTicketImport(t));
        }
        return list;
    }
}