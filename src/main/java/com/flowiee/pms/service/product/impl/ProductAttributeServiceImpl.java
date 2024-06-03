package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductAttribute;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.product.ProductAttributeRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductAttributeService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.utils.LogUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductAttributeServiceImpl extends BaseService implements ProductAttributeService {
    private static final String mvModule = MODULE.PRODUCT.name();
    private static final String mainObjectName = "ProductAttribute";

    @Autowired
    private ProductAttributeRepository productAttributeRepo;
    @Autowired
    private ProductHistoryService productHistoryService;

    @Override
    public List<ProductAttribute> findAll() {
        return this.findAll(-1, -1, null).getContent();
    }

    @Override
    public Page<ProductAttribute> findAll(int pageSize, int pageNum, Integer pProductDetailId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("sort"));
        }
        return productAttributeRepo.findByProductVariantId(pProductDetailId, pageable);
    }

    @Override
    public Optional<ProductAttribute> findById(Integer attributeId) {
        return productAttributeRepo.findById(attributeId);
    }

    @Override
    public ProductAttribute save(ProductAttribute productAttribute) {
        ProductAttribute productAttributeSaved = productAttributeRepo.save(productAttribute);
        systemLogService.writeLogCreate(mvModule, ACTION.PRO_PRD_U.name(), mainObjectName, "Thêm mới thuộc tính sản phẩm", productAttributeSaved.getAttributeName());
        return productAttributeSaved;
    }

    @Override
    public ProductAttribute update(ProductAttribute attribute, Integer attributeId) {
        Optional<ProductAttribute> attributeOptional = this.findById(attributeId);
        if (attributeOptional.isEmpty()) {
            throw new BadRequestException();
        }
        ProductAttribute attributeBefore = ObjectUtils.clone(attributeOptional.get());
        attribute.setId(attributeId);
        ProductAttribute attributeUpdated = productAttributeRepo.save(attribute);

        String logTitle = "Cập nhật thuộc tính sản phẩm";
        Map<String, Object[]> logChanges = LogUtils.logChanges(attributeBefore, attributeUpdated);
        productHistoryService.save(logChanges, logTitle, attributeUpdated.getProductDetail().getProduct().getId(), attributeUpdated.getProductDetail().getId(), attributeUpdated.getId());
        systemLogService.writeLogUpdate(mvModule, ACTION.PRO_PRD_U.name(), mainObjectName, "Cập nhật thuộc tính sản phẩm", logChanges);

        return attributeUpdated;
    }

    @Override
    public String delete(Integer attributeId) {
        Optional<ProductAttribute> attributeToDelete = this.findById(attributeId);
        if (attributeToDelete.isEmpty()) {
            throw new NotFoundException("Product attribute not found!");
        }
        productAttributeRepo.deleteById(attributeId);
        systemLogService.writeLogDelete(mvModule, ACTION.PRO_PRD_U.name(), mainObjectName, "Xóa thuộc tính sản phẩm", attributeToDelete.get().getAttributeName());
        return MessageUtils.DELETE_SUCCESS;
    }
}