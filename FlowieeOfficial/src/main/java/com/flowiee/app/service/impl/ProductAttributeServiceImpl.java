package com.flowiee.app.service.impl;

import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.ProductAttribute;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.repository.product.ProductAttributeRepository;
import com.flowiee.app.service.product.ProductAttributeService;
import com.flowiee.app.service.system.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {
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