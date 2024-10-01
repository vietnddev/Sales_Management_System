package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductReview;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.product.ProductReviewRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductReviewService;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductReviewServiceImpl extends BaseService implements ProductReviewService {
    ProductReviewRepository productReviewRepository;

    @Override
    public List<ProductReview> findAll() {
        return productReviewRepository.findAll();
    }

    @Override
    public Optional<ProductReview> findById(Integer productReviewId) {
        return productReviewRepository.findById(productReviewId);
    }

    @Override
    public ProductReview save(ProductReview productReview) {
        return productReviewRepository.save(productReview);
    }

    @Override
    public ProductReview update(ProductReview productReview, Integer productReviewId) {
        ProductReview existingReview = productReviewRepository.findById(productReviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found!"));
        existingReview.setReviewContent(productReview.getReviewContent());
        existingReview.setRating(productReview.getRating());
        return productReviewRepository.save(existingReview);
    }

    @Override
    public String delete(Integer productReviewId) {
        productReviewRepository.deleteById(productReviewId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public Page<ProductReview> findByProduct(Integer pProductId) {
        return productReviewRepository.findByProduct(pProductId, Pageable.unpaged());
    }
}