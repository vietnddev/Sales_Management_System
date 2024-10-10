package com.flowiee.pms.service.product;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.utils.constants.PID;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInfoService extends BaseCurdService<ProductDTO> {
    Page<ProductDTO> findAll(PID pPID, int pageSize, int pageNum, String pTxtSearch, Integer pBrand, Integer pProductType, Integer pColor, Integer pSize, Integer pUnit, String pStatus);

    Page<ProductDTO> findClothes(int pageSize, int pageNum, String pTxtSearch, Integer pBrand, Integer pProductType, Integer pColor, Integer pSize, Integer pUnit, String pStatus);

    Page<ProductDTO> findFruits(int pageSize, int pageNum, String pTxtSearch, String pStatus);

    Page<ProductDTO> findSouvenirs(int pageSize, int pageNum, String pTxtSearch, Integer pColor, String pStatus);

    List<Product> findProductsIdAndProductName();

    boolean productInUse(Integer productId);
}