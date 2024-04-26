package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    @Autowired
    private ProductDetailRepository productVariantRepo;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<ProductVariantDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null).getContent();
    }

    @Override
    public Page<ProductVariantDTO> findAll(int pageSize, int pageNum, Integer pProductId, Integer pTicketImport, Integer pColor, Integer pSize, Integer pFabricType) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize);
        }
        List<ProductDetail> productVariants = productVariantRepo.findAll(pProductId, pTicketImport, pColor, pSize, pFabricType, pageable);
        return new PageImpl<>(ProductVariantDTO.fromProductVariants(productVariants), pageable, productVariants.size());
    }

    @Override
    public Optional<ProductVariantDTO> findById(Integer pProductVariantId) {
        Optional<ProductDetail> productVariant = productVariantRepo.findById(pProductVariantId);
        return productVariant.map(productDetail -> Optional.of(ProductVariantDTO.fromProductVariant(productDetail))).orElse(null);
    }

    @Override
    public ProductVariantDTO save(ProductVariantDTO productVariantDTO) {
        try {
            ProductDetail pVariant = ProductDetail.fromProductVariantDTO(productVariantDTO);
            pVariant.setStorageQty(0);
            pVariant.setSoldQty(0);
            pVariant.setStatus(AppConstants.PRODUCT_STATUS.A.name());
            pVariant.setVariantCode(CommonUtils.now("yyyyMMddHHmmss"));
            ProductDetail productDetailSaved = productVariantRepo.save(pVariant);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRODUCT_UPDATE.name(), "Thêm mới biến thể sản phẩm: " + pVariant);
            logger.info("Insert productVariant success! " + pVariant);
            return ProductVariantDTO.fromProductVariant(productDetailSaved);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product variant"), ex);
        }
    }

    @Override
    public ProductVariantDTO update(ProductVariantDTO productDetail, Integer productVariantId) {
        Optional<ProductVariantDTO> productDetailBefore = this.findById(productVariantId);
        if (productDetailBefore.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            productDetail.setId(productVariantId);
            ProductDetail productDetailUpdated = productVariantRepo.save(productDetail);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật biến thể sản phẩm: " + productDetailBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + productDetail);
            logger.info("Update productVariant success! {}", productDetail);
            return ProductVariantDTO.fromProductVariant(productDetailUpdated);
        } catch (Exception e) {
            throw new AppException("Update productVariant fail! " + productDetail.toString(), e);
        }
    }

    @Override
    public String delete(Integer productVariantId) {
        Optional<ProductVariantDTO> productDetailToDelete = this.findById(productVariantId);
        if (productDetailToDelete.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            productVariantRepo.deleteById(productVariantId);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRODUCT_UPDATE.name(), "Xóa biến thể sản phẩm: " + productDetailToDelete.toString());
            logger.info("Delete productVariant success! {}", productDetailToDelete);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException("Delete productVariant fail! id=" + productVariantId, ex);
        }
    }

    @Override
    public boolean isProductVariantExists(int productId, int colorId, int sizeId, int fabricTypeId) {
        ProductDetail productDetail = productVariantRepo.findByColorAndSize(productId, colorId, sizeId, fabricTypeId);
        return ObjectUtils.isNotEmpty(productDetail);
    }
}