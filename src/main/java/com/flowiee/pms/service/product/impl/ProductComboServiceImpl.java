package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductCombo;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.product.ProductComboRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductComboService;
import com.flowiee.pms.utils.constants.MasterObject;
import com.flowiee.pms.utils.constants.MessageCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductComboServiceImpl extends BaseService implements ProductComboService {
    private final ProductComboRepository productComboRepository;

    public ProductComboServiceImpl(ProductComboRepository productComboRepository) {
        this.productComboRepository = productComboRepository;
    }

    @Override
    public Page<ProductCombo> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("startTime").ascending());
        }
        return productComboRepository.findAll(pageable);
    }

    @Override
    public List<ProductCombo> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Optional<ProductCombo> findById(Integer comboId) {
        return productComboRepository.findById(comboId);
    }

    @Override
    public ProductCombo save(ProductCombo productCombo) {
        ProductCombo comboSaved = productComboRepository.save(productCombo);
        systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_CBO_C, MasterObject.ProductCombo, "Thêm mới combo sản phẩm", comboSaved.getComboName());
        return comboSaved;
    }

    @Override
    public ProductCombo update(ProductCombo productCombo, Integer comboId) {
        if (this.findById(comboId).isEmpty()) {
            throw new ResourceNotFoundException("Combo not found");
        }
        productCombo.setId(comboId);
        ProductCombo comboUpdated = productComboRepository.save(productCombo);
        systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_CBO_C, MasterObject.ProductCombo, "Cập nhật combo sản phẩm", comboUpdated.getComboName());
        return comboUpdated;
    }

    @Override
    public String delete(Integer comboId) {
        Optional<ProductCombo> comboBefore = this.findById(comboId);
        if (comboBefore.isEmpty()) {
            throw new ResourceNotFoundException("Product combo not found!");
        }
        productComboRepository.deleteById(comboId);
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_CBO_C, MasterObject.ProductCombo, "Cập nhật combo sản phẩm", comboBefore.get().getComboName());
        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}