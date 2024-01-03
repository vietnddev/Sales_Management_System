package com.flowiee.app.service.impl;

import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.ProductAttribute;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.repository.ProductAttributeRepository;
import com.flowiee.app.service.ProductAttributeService;
import com.flowiee.app.service.SystemLogService;
import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private static final String module = SystemModule.PRODUCT.getLabel();

    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<ProductAttribute> findAll() {
        return productAttributeRepository.findAll();
    }

    @Override
    public List<ProductAttribute> getAllAttributes(int productVariantID){
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantID);
        return productAttributeRepository.findByProductVariantId(productVariant);
    }

    @Override
    public String save(ProductAttribute productAttribute){
        productAttributeRepository.save(productAttribute);
        systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Thêm mới thuộc tính sản phẩm");
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(ProductAttribute attribute, Integer attributeId) {
        systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Cập nhật thuộc tính sản phẩm");
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public ProductAttribute findById(Integer attributeID){
    	return productAttributeRepository.findById(attributeID).orElse(null);
    }

    @Override
    public String delete(Integer attributeId) {
    	productAttributeRepository.deleteById(attributeId);
        systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Xóa thuộc tính sản phẩm");
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}