package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.*;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.product.ProductDescriptionRepository;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.model.dto.VoucherApplyDTO;
import com.flowiee.pms.model.dto.VoucherInfoDTO;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.repository.product.ProductRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.*;
import com.flowiee.pms.service.sales.VoucherApplyService;
import com.flowiee.pms.service.sales.VoucherService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.converter.ProductConvert;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductInfoServiceImpl extends BaseService implements ProductInfoService {
    VoucherService mvVoucherInfoService;
    ProductRepository mvProductRepository;
    CategoryRepository mvCategoryRepository;
    VoucherApplyService mvVoucherApplyService;
    //ProductImageService mvProductImageService;
    ProductVariantService mvProductVariantService;
    ProductHistoryService mvProductHistoryService;
    ProductStatisticsService mvProductStatisticsService;
    ProductDescriptionRepository mvProductDescriptionRepository;

    @Override
    public List<ProductDTO> findAll() {
        return this.findAll(null, -1, -1, null, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<ProductDTO> findAll(PID pPID , int pageSize, int pageNum, String pTxtSearch, Long pBrand, Long pProductType,
                                    Long pColor, Long pSize, Long pUnit, String pStatus) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<Product> products = mvProductRepository.findAll(pPID.getId(), pTxtSearch, pBrand, pProductType, pColor, pSize, pUnit, pStatus, pageable);
        List<ProductDTO> productDTOs = ProductConvert.convertToDTOs(products);
        this.setImageActiveAndLoadVoucherApply(productDTOs);
        this.setInfoVariantOfProduct(productDTOs);
        return new PageImpl<>(productDTOs, pageable, products.getTotalElements());
    }

    @Override
    public Page<ProductDTO> findClothes(int pageSize, int pageNum, String pTxtSearch, Long pBrand, Long pProductType, Long pColor, Long pSize, Long pUnit, String pStatus) {
        return findAll(PID.CLOTHES, pageSize, pageNum, pTxtSearch, pBrand, pProductType, pColor, pSize, pUnit, pStatus);
    }

    @Override
    public Page<ProductDTO> findFruits(int pageSize, int pageNum, String pTxtSearch, String pStatus) {
        return findAll(PID.FRUIT, pageSize, pageNum, pTxtSearch, null, null, null, null, null, pStatus);
    }

    @Override
    public Page<ProductDTO> findSouvenirs(int pageSize, int pageNum, String pTxtSearch, Long pColor, String pStatus) {
        return findAll(PID.SOUVENIR, pageSize, pageNum, pTxtSearch, null, null, pColor, null, null, pStatus);
    }

    @Override
    public List<Product> findProductsIdAndProductName() {
        List<Product> products = new ArrayList<>();
        for (Object[] objects : mvProductRepository.findIdAndName(ProductStatus.A.name())) {
            products.add(new Product(Integer.parseInt(String.valueOf(objects[0])), String.valueOf(objects[1])));
        }
        return products;
    }

    @Override
    public ProductDTO saveClothes(ProductDTO productDTO) {
        productDTO.setPID(PID.CLOTHES.getId());
        return save(productDTO);
    }

    @Override
    public ProductDTO saveSouvenir(ProductDTO productDTO) {
        productDTO.setPID(PID.SOUVENIR.getId());
        return save(productDTO);
    }

    @Override
    public ProductDTO saveFruit(ProductDTO productDTO) {
        productDTO.setPID(PID.FRUIT.getId());
        return save(productDTO);
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {
        Optional<Product> productOpt = mvProductRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            ProductDescription productDescription = product.getProductDescription();
            return Optional.of(ProductConvert.convertToDTO(product, productDescription != null ? productDescription.getDescription() : null));
        }
        return Optional.empty();
    }

    @Override
    public ProductDTO save(ProductDTO product) {
        try {
            Product productToSave = ProductConvert.convertToEntity(product);
            //productToSave.setCreatedBy(CommonUtils.getUserPrincipal().getId());
            productToSave.setStatus(ProductStatus.I.name());
            Product productSaved = mvProductRepository.save(productToSave);

            ProductDescription productDescription = null;
            if (ObjectUtils.isNotEmpty(product.getDescription())) {
                productDescription = mvProductDescriptionRepository.save(ProductDescription.builder()
                        .product(productSaved)
                        .description(product.getDescription()).build());
            }

            systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_PRD_C, MasterObject.Product, "Thêm mới sản phẩm", product.getProductName());
            logger.info("Insert product success! {}", product);
            return ProductConvert.convertToDTO(productSaved, productDescription.getDescription());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "product"), ex);
        }
    }

    @Transactional
    @Override
    public ProductDTO update(ProductDTO productDTO, Long productId) {
        Optional<Product> productOpt = mvProductRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new BadRequestException();
        }
        Product lvProduct = productOpt.get();
        Product productBefore = ObjectUtils.clone(productOpt.get());

        //product.setId(productId);
        lvProduct.setProductName(productDTO.getProductName());
        lvProduct.setProductType(new Category(productDTO.getProductTypeId()));
        lvProduct.setUnit(new Category(productDTO.getUnitId()));
        lvProduct.setBrand(new Category(productDTO.getBrandId()));
        lvProduct.setStatus(productDTO.getStatus());

        ProductDescription productDescription = lvProduct.getProductDescription();
        if (productDescription != null) {
            productDescription.setDescription(productDTO.getDescription());
        } else {
            productDescription = ProductDescription.builder()
                .product(lvProduct)
                .description(productDTO.getDescription()).build();
        }
        ProductDescription productDescriptionUpdated = mvProductDescriptionRepository.save(productDescription);

        lvProduct.setProductDescription(productDescriptionUpdated);
        Product productUpdated = mvProductRepository.save(lvProduct);

        String logTitle = "Cập nhật sản phẩm: " + productUpdated.getProductName();
        ChangeLog changeLog = new ChangeLog(productBefore, productUpdated);
        mvProductHistoryService.save(changeLog.getLogChanges(), logTitle, productUpdated.getId(), null, null);
        systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.Product, logTitle, changeLog);
        logger.info("Update product success! productId={}", productId);
        return ProductConvert.convertToDTO(productUpdated, productDescriptionUpdated.getDescription());
    }

    @Transactional
    @Override
    public String delete(Long id) {
        try {
            Optional<ProductDTO> productToDelete = this.findById(id);
            if (productToDelete.isEmpty()) {
                throw new ResourceNotFoundException("Product not found!");
            }
            if (productInUse(id)) {
                throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
            }
            mvProductRepository.deleteById(id);
            systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_PRD_D, MasterObject.Product, "Xóa sản phẩm", productToDelete.get().getProductName());
            logger.info("Delete product success! productId={}", id);
            return MessageCode.DELETE_SUCCESS.getDescription();
        } catch (RuntimeException ex) {
            throw new AppException("Delete product fail! productId=" + id, ex);
        }
    }

    @Override
    public boolean productInUse(Long productId) throws RuntimeException {
        return !mvProductVariantService.findAll(-1, -1, productId, null, null, null, null, null).getContent().isEmpty();
    }

    private void setImageActiveAndLoadVoucherApply(List<ProductDTO> products) {
        if (products == null) {
            return;
        }
        for (ProductDTO p : products) {
            FileStorage imageActive = p.getImage();//mvProductImageService.findImageActiveOfProduct(p.getId());
            if (imageActive != null) {
                p.setImageActive("/" + imageActive.getDirectoryPath() + "/" + imageActive.getStorageName());
            }
            List<Long> listVoucherInfoId = new ArrayList<>();
            for (VoucherApplyDTO voucherApplyDTO : mvVoucherApplyService.findByProductId(p.getId())) {
                listVoucherInfoId.add(voucherApplyDTO.getVoucherInfoId());
            }
            if (!listVoucherInfoId.isEmpty()) {
                List<VoucherInfoDTO> voucherInfoDTOs = mvVoucherInfoService.findAll(-1, -1, listVoucherInfoId, null, null, null, VoucherStatus.A.name()).getContent();
                p.setListVoucherInfoApply(voucherInfoDTOs);
            }
        }
    }

    private void setInfoVariantOfProduct(List<ProductDTO> products) {
        if (products == null) {
            return;
        }
        for (ProductDTO p : products) {
            LinkedHashMap<String, String> variantInfo = new LinkedHashMap<>();
            int totalQtyStorage = 0;
            int totalDefective = 0;
            int totalQtySell = mvProductStatisticsService.findProductVariantTotalQtySell(p.getId());

            for (Category color : mvCategoryRepository.findColorOfProduct(p.getId())) {
                StringBuilder sizeName = new StringBuilder();
                List<Category> listSize = mvCategoryRepository.findSizeOfColorOfProduct(p.getId(), color.getId());
                for (int i = 0; i < listSize.size(); i++) {
                    Category categorySize = listSize.get(i);
                    int qtyStorage = mvProductStatisticsService.findProductVariantQuantityBySizeOfEachColor(p.getId(), color.getId(), categorySize.getId());
                    if (i == listSize.size() - 1) {
                        sizeName.append(categorySize.getName()).append(" (").append(qtyStorage).append(")");
                    } else {
                        sizeName.append(categorySize.getName()).append(" (").append(qtyStorage).append(")").append(", ");
                    }
                    totalQtyStorage += qtyStorage;
                }
                variantInfo.put(color.getName(), sizeName.toString());//Đen: S (5)
            }
            p.setProductVariantInfo(variantInfo);
            p.setTotalQtyStorage(totalQtyStorage);
            p.setTotalQtySell(totalQtySell);
            p.setTotalQtyAvailableSales(totalQtyStorage - totalDefective);
        }
    }
}