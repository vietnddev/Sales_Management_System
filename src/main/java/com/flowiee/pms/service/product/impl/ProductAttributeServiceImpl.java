package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductAttribute;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.repository.product.ProductAttributeRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductAttributeService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductAttributeServiceImpl extends BaseService implements ProductAttributeService {
    private static final String mvModule = MODULE.PRODUCT.name();

    @Autowired
    private ProductAttributeRepository productAttributeRepo;
    @Autowired
    private SystemLogService systemLogService;

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
        systemLogService.writeLog(mvModule, ACTION.PRO_PRD_U.name(), "Thêm mới thuộc tính sản phẩm");
        return productAttributeSaved;
    }

    @Override
    public ProductAttribute update(ProductAttribute attribute, Integer attributeId) {
        attribute.setId(attributeId);
        ProductAttribute productAttributeUpdated = productAttributeRepo.save(attribute);
        systemLogService.writeLog(mvModule, ACTION.PRO_PRD_U.name(), "Cập nhật thuộc tính sản phẩm");
        return productAttributeUpdated;
    }

    @Override
    public String delete(Integer attributeId) {
        if (this.findById(attributeId).isEmpty()) {
            throw new NotFoundException("Product attribute not found!");
        }
        productAttributeRepo.deleteById(attributeId);
        systemLogService.writeLog(mvModule, ACTION.PRO_PRD_U.name(), "Xóa thuộc tính sản phẩm");
        return MessageUtils.DELETE_SUCCESS;
    }
}