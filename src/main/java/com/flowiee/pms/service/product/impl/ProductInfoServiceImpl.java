package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.*;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
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
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductInfoServiceImpl extends BaseService implements ProductInfoService {
    ProductRepository        productsRepo;
    CategoryRepository       categoryRepo;
    VoucherService           voucherInfoService;
    VoucherApplyService      voucherApplyService;
    ProductImageService      productImageService;
    ProductVariantService    productVariantService;
    ProductHistoryService    productHistoryService;
    ProductStatisticsService productStatisticsService;

    public ProductInfoServiceImpl(ProductRepository productsRepo, ProductHistoryService productHistoryService, @Lazy VoucherService voucherInfoService, @Lazy VoucherApplyService voucherApplyService, CategoryRepository categoryRepo, ProductVariantService productVariantService, ProductStatisticsService productStatisticsService, ProductImageService productImageService) {
        this.productsRepo = productsRepo;
        this.productHistoryService = productHistoryService;
        this.voucherInfoService = voucherInfoService;
        this.voucherApplyService = voucherApplyService;
        this.categoryRepo = categoryRepo;
        this.productVariantService = productVariantService;
        this.productStatisticsService = productStatisticsService;
        this.productImageService = productImageService;
    }

    @Override
    public List<ProductDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<ProductDTO> findAll(int pageSize, int pageNum, String pTxtSearch, Integer pBrand, Integer pProductType,
                                    Integer pColor, Integer pSize, Integer pUnit, String pStatus) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<Product> products = productsRepo.findAll(pTxtSearch, pBrand, pProductType, pUnit, pStatus, pageable);
        List<ProductDTO> productDTOs = ProductConvert.convertToDTOs(products);
        this.setImageActiveAndLoadVoucherApply(productDTOs);
        this.setInfoVariantOfProduct(productDTOs);
        return new PageImpl<>(productDTOs, pageable, products.getTotalElements());
    }

    @Override
    public List<Product> findProductsIdAndProductName() {
        List<Product> products = new ArrayList<>();
        for (Object[] objects : productsRepo.findIdAndName(ProductStatus.A.name())) {
            products.add(new Product(Integer.parseInt(String.valueOf(objects[0])), String.valueOf(objects[1])));
        }
        return products;
    }

    @Override
    public Optional<ProductDTO> findById(Integer id) {
        Optional<Product> product = productsRepo.findById(id);
        return product.map(p -> Optional.of(ProductConvert.convertToDTO(p))).orElse(null);
    }

    @Override
    public ProductDTO save(ProductDTO product) {
        try {
            Product productToSave = ProductConvert.convertToEntity(product);
            productToSave.setCreatedBy(CommonUtils.getUserPrincipal().getId());
            productToSave.setDescription(product.getDescription() != null ? product.getDescription() : "");
            productToSave.setStatus(ProductStatus.I.name());
            Product productSaved = productsRepo.save(productToSave);
            systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_PRD_C, MasterObject.Product, "Thêm mới sản phẩm", product.getProductName());
            logger.info("Insert product success! {}", product);
            return ProductConvert.convertToDTO(productSaved);
        } catch (RuntimeException ex) {
            throw new AppException("Insert product fail!", ex);
        }
    }

    @Transactional
    @Override
    public ProductDTO update(ProductDTO productDTO, Integer productId) {
        Product productToUpdate = ProductConvert.convertToEntity(productDTO);
        if (productToUpdate.getDescription().isEmpty()) {
            productToUpdate.setDescription("-");
        }
        Optional<ProductDTO> productOptional = this.findById(productId);
        if (productOptional.isEmpty()) {
            throw new BadRequestException();
        }
        Product productBefore = ObjectUtils.clone(productOptional.get());
        productToUpdate.setId(productId);
        Product productUpdated = productsRepo.save(productToUpdate);

        String logTitle = "Cập nhật sản phẩm: " + productUpdated.getProductName();
        ChangeLog changeLog = new ChangeLog(productBefore, productUpdated);
        productHistoryService.save(changeLog.getLogChanges(), logTitle, productUpdated.getId(), null, null);
        systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.Product, logTitle, changeLog);
        logger.info("Update product success! productId={}", productId);
        return ProductConvert.convertToDTO(productUpdated);
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        try {
            Optional<ProductDTO> productToDelete = this.findById(id);
            if (productToDelete.isEmpty()) {
                throw new ResourceNotFoundException("Product not found!");
            }
            if (productInUse(id)) {
                throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
            }
            productsRepo.deleteById(id);
            systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_PRD_D, MasterObject.Product, "Xóa sản phẩm", productToDelete.get().getProductName());
            logger.info("Delete product success! productId={}", id);
            return MessageCode.DELETE_SUCCESS.getDescription();
        } catch (RuntimeException ex) {
            throw new AppException("Delete product fail! productId=" + id, ex);
        }
    }

    @Override
    public boolean productInUse(Integer productId) throws RuntimeException {
        return !productVariantService.findAll(-1, -1, productId, null, null, null, null).getContent().isEmpty();
    }

    private void setImageActiveAndLoadVoucherApply(List<ProductDTO> products) {
        if (products == null) {
            return;
        }
        for (ProductDTO p : products) {
            FileStorage imageActive = productImageService.findImageActiveOfProduct(p.getId());
            if (imageActive != null) {
                p.setImageActive("/" + imageActive.getDirectoryPath() + "/" + imageActive.getStorageName());
            }
            List<Integer> listVoucherInfoId = new ArrayList<>();
            for (VoucherApplyDTO voucherApplyDTO : voucherApplyService.findByProductId(p.getId())) {
                listVoucherInfoId.add(voucherApplyDTO.getVoucherInfoId());
            }
            if (!listVoucherInfoId.isEmpty()) {
                List<VoucherInfoDTO> voucherInfoDTOs = voucherInfoService.findAll(-1, -1, listVoucherInfoId, null, null, null, VoucherStatus.A.name()).getContent();
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
            for (Category color : categoryRepo.findColorOfProduct(p.getId())) {
                StringBuilder sizeName = new StringBuilder();
                List<Category> listSize = categoryRepo.findSizeOfColorOfProduct(p.getId(), color.getId());
                for (int i = 0; i < listSize.size(); i++) {
                    int qtyStorage = productStatisticsService.findProductVariantQuantityBySizeOfEachColor(p.getId(), color.getId(), listSize.get(i).getId());
                    if (i == listSize.size() - 1) {
                        sizeName.append(listSize.get(i).getName()).append(" (").append(qtyStorage).append(")");
                    } else {
                        sizeName.append(listSize.get(i).getName()).append(" (").append(qtyStorage).append(")").append(", ");
                    }
                    totalQtyStorage += qtyStorage;
                }
                variantInfo.put(color.getName(), sizeName.toString());//Đen: S (5)
            }
            p.setProductVariantInfo(variantInfo);
            p.setTotalQtyStorage(totalQtyStorage);
            p.setTotalQtySell(productStatisticsService.findProductVariantTotalQtySell(p.getId()));
            int totalDefective = 0;
            p.setTotalQtyAvailableSales(totalQtyStorage - totalDefective);
        }
    }
}