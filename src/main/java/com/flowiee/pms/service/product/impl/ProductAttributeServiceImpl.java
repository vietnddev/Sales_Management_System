package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductAttribute;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.product.ProductAttributeRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductAttributeService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.utils.constants.MasterObject;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductAttributeServiceImpl extends BaseService implements ProductAttributeService {
    ProductAttributeRepository mvProductAttributeRepository;
    ProductHistoryService      mvProductHistoryService;

    @Override
    public List<ProductAttribute> findAll() {
        return this.findAll(-1, -1, null).getContent();
    }

    @Override
    public Page<ProductAttribute> findAll(int pageSize, int pageNum, Long pProductDetailId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("sort"));
        }
        return mvProductAttributeRepository.findByProductVariantId(pProductDetailId, pageable);
    }

    @Override
    public Optional<ProductAttribute> findById(Long attributeId) {
        return mvProductAttributeRepository.findById(attributeId);
    }

    @Override
    public ProductAttribute save(ProductAttribute productAttribute) {
        ProductAttribute productAttributeSaved = mvProductAttributeRepository.save(productAttribute);
        systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductAttribute, "Thêm mới thuộc tính sản phẩm", productAttributeSaved.getAttributeName());
        return productAttributeSaved;
    }

    @Override
    public ProductAttribute update(ProductAttribute attribute, Long attributeId) {
        Optional<ProductAttribute> attributeOptional = this.findById(attributeId);
        if (attributeOptional.isEmpty()) {
            throw new BadRequestException();
        }
        ProductAttribute attributeBefore = ObjectUtils.clone(attributeOptional.get());
        attribute.setId(attributeId);
        ProductAttribute attributeUpdated = mvProductAttributeRepository.save(attribute);

        String logTitle = "Cập nhật thuộc tính sản phẩm";
        ChangeLog changeLog = new ChangeLog(attributeBefore, attributeUpdated);
        mvProductHistoryService.save(changeLog.getLogChanges(), logTitle, attributeUpdated.getProductDetail().getProduct().getId(), attributeUpdated.getProductDetail().getId(), attributeUpdated.getId());
        systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductAttribute, "Cập nhật thuộc tính sản phẩm", changeLog);

        return attributeUpdated;
    }

    @Override
    public String delete(Long attributeId) {
        Optional<ProductAttribute> attributeToDelete = this.findById(attributeId);
        if (attributeToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Product attribute not found!");
        }
        mvProductAttributeRepository.deleteById(attributeId);
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductAttribute, "Xóa thuộc tính sản phẩm", attributeToDelete.get().getAttributeName());
        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}