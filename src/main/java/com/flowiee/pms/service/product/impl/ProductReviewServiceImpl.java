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
    ProductReviewRepository mvProductReviewRepository;

    @Override
    public List<ProductReview> findAll() {
        return mvProductReviewRepository.findAll();
    }

    @Override
    public Optional<ProductReview> findById(Long productReviewId) {
        return mvProductReviewRepository.findById(productReviewId);
    }

    @Override
    public ProductReview save(ProductReview productReview) {
        return mvProductReviewRepository.save(productReview);
    }

    @Override
    public ProductReview update(ProductReview productReview, Long productReviewId) {
        ProductReview existingReview = mvProductReviewRepository.findById(productReviewId).orElseThrow(() -> new ResourceNotFoundException("Review not found!"));
        existingReview.setReviewContent(productReview.getReviewContent());
        existingReview.setRating(productReview.getRating());
        return mvProductReviewRepository.save(existingReview);
    }

    @Override
    public String delete(Long productReviewId) {
        mvProductReviewRepository.deleteById(productReviewId);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public Page<ProductReview> findByProduct(Long pProductId) {
        return mvProductReviewRepository.findByProduct(pProductId, Pageable.unpaged());
    }
}