package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.entity.product.ProductAttribute;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.repository.product.ProductHistoryRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductHistoryServiceImpl extends BaseService implements ProductHistoryService {
    ProductHistoryRepository productHistoryRepo;

    @Override
    public List<ProductHistory> findAll() {
        return productHistoryRepo.findAll();
    }

    @Override
    public Optional<ProductHistory> findById(Integer productHistoryId) {
        return productHistoryRepo.findById(productHistoryId);
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
        return MessageCode.DELETE_SUCCESS.getDescription();
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

    @Override
    public List<ProductHistory> save(Map<String, Object[]> logChanges, String title, Integer productBaseId, Integer productVariantId, Integer productAttributeId) {
        List<ProductHistory> logSaved = new ArrayList<>();
        for (Map.Entry<String, Object[]> entry : logChanges.entrySet()) {
            String field = entry.getKey();
            String oldValue = entry.getValue()[0].toString();
            String newValue = entry.getValue()[1].toString();
            ProductHistory productHistory = new ProductHistory();
            productHistory.setTitle(title);
            if (productBaseId != null) {
                productHistory.setProduct(new Product(productBaseId));
            }
            if (productVariantId != null) {
                productHistory.setProductDetail(new ProductDetail(productVariantId));
            }
            if (productAttributeId != null) {
                productHistory.setProductAttribute(new ProductAttribute(productAttributeId));
            }
            productHistory.setField(field);
            productHistory.setOldValue(oldValue);
            productHistory.setNewValue(newValue);
            logSaved.add(this.save(productHistory));
        }
        return logSaved;
    }
}