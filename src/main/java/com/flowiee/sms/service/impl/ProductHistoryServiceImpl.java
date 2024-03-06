package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.ProductHistory;
import com.flowiee.sms.repository.ProductHistoryRepository;
import com.flowiee.sms.service.ProductHistoryService;
import com.flowiee.sms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductHistoryServiceImpl implements ProductHistoryService {
    @Autowired
    private ProductHistoryRepository productHistoryRepo;

    @Override
    public List<ProductHistory> findAll() {
        return productHistoryRepo.findAll();
    }

    @Override
    public ProductHistory findById(Integer productHistoryId) {
        return productHistoryRepo.findById(productHistoryId).orElse(null);
    }

    @Override
    public ProductHistory save(ProductHistory productHistory) {
        return productHistoryRepo.save(productHistory);
    }

    @Override
    public ProductHistory update(ProductHistory productHistory, Integer productHistoryId) {
        productHistory.setId(productHistoryId);
        return productHistoryRepo.save(productHistory);
    }

    @Override
    public String delete(Integer productHistoryId) {
        productHistoryRepo.deleteById(productHistoryId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<ProductHistory> findByProduct(Integer productId) {
        return productHistoryRepo.findByProductId(productId);
    }

    @Override
    public List<ProductHistory> findPriceChange(Integer productDetailId) {
        List<ProductHistory> prices =  productHistoryRepo.findHistoryChangeOfProductDetail(productDetailId, "PRICE");
        for (ProductHistory priceChange : prices) {
            if (priceChange.getProduct() != null) {
                priceChange.setProductId(priceChange.getProduct().getId());
                if (priceChange.getProductDetail() != null) {
                    priceChange.setProductVariantId(priceChange.getProductDetail().getId());
                }
            }
        }
        return prices;
    }
}