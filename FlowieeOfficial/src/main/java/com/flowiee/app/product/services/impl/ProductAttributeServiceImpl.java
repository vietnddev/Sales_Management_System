package com.flowiee.app.product.services.impl;

import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.system.service.SystemLogService;
import com.flowiee.app.product.entity.ProductAttribute;
import com.flowiee.app.product.repository.ProductAttributeRepository;
import com.flowiee.app.product.services.ProductAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
    private static final Logger logger = LoggerFactory.getLogger(ProductAttributeServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.getLabel();

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
        return productAttributeRepository.findByBienTheSanPham(productVariant);
    }

    @Override
    public String save(ProductAttribute productAttribute){
        productAttributeRepository.save(productAttribute);
        systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Thêm mới thuộc tính sản phẩm");
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(ProductAttribute attribute, Integer attributeId) {
        systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật thuộc tính sản phẩm");
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public ProductAttribute findById(Integer attributeID){
    	return productAttributeRepository.findById(attributeID).orElse(null);
    }

    @Override
    public String delete(Integer attributeId) {
    	productAttributeRepository.deleteById(attributeId);
        systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Xóa thuộc tính sản phẩm");
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}