package com.flowiee.pms.service.product;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.model.dto.ProductVariantTempDTO;
import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductVariantService extends CrudService<ProductVariantDTO> {
    Page<ProductVariantDTO> findAll(int pageSize, int pageNum, Integer pProductId, Integer pTicketImport, Integer pColor, Integer pSize, Integer pFabricType);

    boolean isProductVariantExists(int productId, int colorId, int sizeId, int fabricTypeId);

    List<ProductVariantTempDTO> findStorageHistory(Integer productVariantId);
}