package com.flowiee.pms.service.product;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.entity.product.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInfoService extends CrudService<ProductDTO> {
    Page<ProductDTO> findAll(int pageSize, int pageNum, String pTxtSearch, Integer pBrand, Integer pProductType, Integer pColor, Integer pSize, Integer pUnit, String pStatus);

    List<Product> findProductsIdAndProductName();

    boolean productInUse(Integer productId);
}