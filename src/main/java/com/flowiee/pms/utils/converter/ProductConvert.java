package com.flowiee.pms.utils.converter;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.model.dto.ProductDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ProductConvert {
    public static Product convertToEntity(ProductDTO inputDTO) {
        Product outputEntity = new Product();
        if (inputDTO != null) {
            outputEntity.setId(inputDTO.getId());
            outputEntity.setProductName(inputDTO.getProductName());
            outputEntity.setDescription(inputDTO.getDescription());
            outputEntity.setStatus(inputDTO.getStatus());
            outputEntity.setCreatedAt(inputDTO.getCreatedAt());
            outputEntity.setCreatedBy(inputDTO.getCreatedBy());

            if (ObjectUtils.isNotEmpty(inputDTO.getProductType()))
                outputEntity.setProductType(inputDTO.getProductType());
            if (outputEntity.getProductType() == null && inputDTO.getProductTypeId() != null)
                outputEntity.setProductType(new Category(inputDTO.getProductTypeId(), null));

            if (ObjectUtils.isNotEmpty(inputDTO.getBrand()))
                outputEntity.setBrand(inputDTO.getBrand());
            if (outputEntity.getBrand() == null && inputDTO.getBrandId() != null)
                outputEntity.setBrand(new Category(inputDTO.getBrandId(), null));

            if (ObjectUtils.isNotEmpty(inputDTO.getUnit()))
                outputEntity.setUnit(inputDTO.getUnit());
            if (outputEntity.getUnit() == null && inputDTO.getUnitId() != null)
                outputEntity.setUnit(new Category(inputDTO.getUnitId(), null));

            if (ObjectUtils.isNotEmpty(inputDTO.getListVariants()))
                outputEntity.setListVariants(inputDTO.getListVariants());

            if (ObjectUtils.isNotEmpty(inputDTO.getListImages()))
                outputEntity.setListImages(inputDTO.getListImages());

            if (ObjectUtils.isNotEmpty(inputDTO.getListProductHistories()))
                outputEntity.setListProductHistories(inputDTO.getListProductHistories());
        }
        return outputEntity;
    }

    public static ProductDTO convertToDTO(Product inputEntity) {
        ProductDTO dto = new ProductDTO();
        if (inputEntity != null) {
            dto.setId(inputEntity.getId());
            dto.setProductName(inputEntity.getProductName());
            if (ObjectUtils.isNotEmpty(inputEntity.getProductType())) {
                dto.setProductType(inputEntity.getProductType());
                dto.setProductTypeId(inputEntity.getProductType().getId());
                dto.setProductTypeName(inputEntity.getProductType().getName());
            }
            if (ObjectUtils.isNotEmpty(inputEntity.getBrand())) {
                dto.setBrand(inputEntity.getBrand());
                dto.setBrandId(inputEntity.getBrand().getId());
                dto.setBrandName(inputEntity.getBrand().getName());
            }
            if (ObjectUtils.isNotEmpty(inputEntity.getUnit())) {
                dto.setUnit(inputEntity.getUnit());
                dto.setUnitId(inputEntity.getUnit().getId());
                dto.setUnitName(inputEntity.getUnit().getName());
            }
            dto.setDescription(inputEntity.getDescription());
            dto.setStatus(inputEntity.getStatus());
            if (ObjectUtils.isNotEmpty(inputEntity.getListVariants())) {
                dto.setProductVariantQty(inputEntity.getListVariants().size());
            }
            dto.setSoldQty(null);
            dto.setCreatedAt(inputEntity.getCreatedAt());
            dto.setCreatedBy(inputEntity.getCreatedBy());
        }
        return dto;
    }

    public static List<Product> convertToEntities(List<ProductDTO> inputDTOs) {
        List<Product> outEntities = new ArrayList<>();
        if (inputDTOs != null) {
            for (ProductDTO p : inputDTOs) {
                outEntities.add(convertToEntity(p));
            }
        }
        return outEntities;
    }

    public static List<ProductDTO> convertToDTOs(List<Product> inputEntities) {
        List<ProductDTO> outDTOs = new ArrayList<>();
        if (inputEntities != null) {
            for (Product p : inputEntities) {
                outDTOs.add(convertToDTO(p));
            }
        }
        return outDTOs;
    }

    public static List<ProductDTO> convertToDTOs(Page<Product> inputEntities) {
        List<ProductDTO> outDTOs = new ArrayList<>();
        if (inputEntities != null) {
            for (Product p : inputEntities.getContent()) {
                outDTOs.add(convertToDTO(p));
            }
        }
        return outDTOs;
    }
}