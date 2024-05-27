package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductCombo;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.repository.product.ProductComboRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductComboService;
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
public class ProductComboServiceImpl extends BaseService implements ProductComboService {
    @Autowired
    private ProductComboRepository productComboRepository;

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
        return productComboRepository.save(productCombo);
    }

    @Override
    public ProductCombo update(ProductCombo productCombo, Integer comboId) {
        if (this.findById(comboId).isEmpty()) {
            throw new NotFoundException("Combo not found");
        }
        productCombo.setId(comboId);
        return productComboRepository.save(productCombo);
    }

    @Override
    public String delete(Integer comboId) {
        productComboRepository.deleteById(comboId);
        return MessageUtils.DELETE_SUCCESS;
    }
}