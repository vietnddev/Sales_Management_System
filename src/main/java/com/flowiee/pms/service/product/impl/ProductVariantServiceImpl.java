package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.model.dto.ProductVariantTempDTO;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.repository.product.ProductDetailTempRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.service.sales.TicketImportService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.LogUtils;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.utils.converter.ProductVariantConvert;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductVariantServiceImpl extends BaseService implements ProductVariantService {
    private static final String mainObjectName = "ProductVariant";

    @Autowired
    private ProductDetailRepository productVariantRepo;
    @Autowired @Lazy
    private TicketImportService ticketImportService;
    @Autowired
    private TicketExportService ticketExportService;
    @Autowired
    private ProductHistoryService productHistoryService;
    @Autowired
    private ProductDetailTempRepository productVariantTempRepo;

    @Override
    public List<ProductVariantDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null).getContent();
    }

    @Override
    public Page<ProductVariantDTO> findAll(int pageSize, int pageNum, Integer pProductId, Integer pTicketImport, Integer pColor, Integer pSize, Integer pFabricType) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize);
        }
        List<ProductDetail> productVariants = productVariantRepo.findAll(pProductId, pColor, pSize, pFabricType, pageable);
        return new PageImpl<>(ProductVariantConvert.entitiesToDTOs(productVariants), pageable, productVariants.size());
    }

    @Override
    public Optional<ProductVariantDTO> findById(Integer pProductVariantId) {
        Optional<ProductDetail> productVariant = productVariantRepo.findById(pProductVariantId);
        return productVariant.map(productDetail -> Optional.ofNullable(ProductVariantConvert.entityToDTO(productDetail))).orElse(null);
    }

    @Override
    public ProductVariantDTO save(ProductVariantDTO inputDTO) {
        try {
            ProductDetail pVariant = ProductVariantConvert.dtoToEntity(inputDTO);
            if (pVariant.getStorageQty() == null)
                pVariant.setStorageQty(0);
            if (pVariant.getSoldQty() == null)
                pVariant.setSoldQty(0);
            if (pVariant.getDefectiveQty() == null)
                pVariant.setDefectiveQty(0);
            pVariant.setStatus(ProductStatus.A.name());
            pVariant.setVariantCode(ObjectUtils.isNotEmpty(inputDTO.getVariantCode()) ? inputDTO.getVariantCode() : CommonUtils.genProductCode());
            pVariant.setRetailPriceDiscount(inputDTO.getRetailPriceDiscount() != null ? inputDTO.getRetailPriceDiscount() : inputDTO.getRetailPrice());
            pVariant.setWholesalePriceDiscount(inputDTO.getWholesalePriceDiscount() != null ? inputDTO.getWholesalePriceDiscount() : inputDTO.getWholesalePrice());
            ProductDetail productDetailSaved = productVariantRepo.save(pVariant);

            if (productDetailSaved.getStorageQty() > 0) {
                String initMessage = "Initialize storage quantity when create new products";

                TicketImport ticketImport = new TicketImport();
                ticketImport.setTitle("Initialize storage");
                ticketImport.setImporter(CommonUtils.getUserPrincipal().getUsername());
                ticketImport.setImportTime(LocalDateTime.now());
                ticketImport.setNote(initMessage);
                ticketImport.setStatus(TicketImportStatus.COMPLETED.name());
                ticketImport.setStorage(new Storage(inputDTO.getStorageIdInitStorageQty()));
                TicketImport ticketImportSaved = ticketImportService.save(ticketImport);

                ProductVariantTemp productVariantTemp = new ProductVariantTemp();
                productVariantTemp.setTicketImport(ticketImportSaved);
                productVariantTemp.setProductVariant(productDetailSaved);
                productVariantTemp.setQuantity(productDetailSaved.getStorageQty());
                productVariantTemp.setNote(initMessage);
                productVariantTempRepo.save(productVariantTemp);
            }
            if (productDetailSaved.getSoldQty() > 0) {
                String initMessage = "Initialize storage quantity when create new products";

                TicketExport ticketExport = new TicketExport();
                ticketExport.setTitle("Initialize storage");
                ticketExport.setExporter(CommonUtils.getUserPrincipal().getUsername());
                ticketExport.setExportTime(LocalDateTime.now());
                ticketExport.setNote(initMessage);
                ticketExport.setStatus(TicketExportStatus.COMPLETED.name());
                ticketExport.setStorage(new Storage(inputDTO.getStorageIdInitStorageQty()));
                TicketExport ticketExportSaved = ticketExportService.save(ticketExport);

                ProductVariantTemp productVariantTemp = new ProductVariantTemp();
                productVariantTemp.setTicketExport(ticketExportSaved);
                productVariantTemp.setProductVariant(productDetailSaved);
                productVariantTemp.setQuantity(productDetailSaved.getStorageQty());
                productVariantTemp.setNote(initMessage);
                productVariantTempRepo.save(productVariantTemp);
            }

            systemLogService.writeLogCreate(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), mainObjectName, "Thêm mới biến thể sản phẩm", pVariant.toStringInsert());
            logger.info("Insert productVariant success! {}", pVariant);
            return ProductVariantConvert.entityToDTO(productDetailSaved);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product variant"), ex);
        }
    }

    @Transactional
    @Override
    public ProductVariantDTO update(ProductVariantDTO productDetail, Integer productVariantId) {
        Optional<ProductDetail> productVariantOptional = productVariantRepo.findById(productVariantId);
        if (productVariantOptional.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            ProductDetail productBeforeUpdate = ObjectUtils.clone(productVariantOptional.get());
            ProductDetail productToUpdate = productVariantOptional.get();
            productToUpdate.setVariantName(productDetail.getVariantName());
            productToUpdate.setPurchasePrice(productDetail.getPurchasePrice());
            productToUpdate.setCostPrice(productDetail.getCostPrice());
            productToUpdate.setRetailPrice(productDetail.getRetailPrice());
            productToUpdate.setRetailPriceDiscount(productDetail.getRetailPriceDiscount());
            productToUpdate.setWholesalePrice(productDetail.getWholesalePrice());
            productToUpdate.setWholesalePriceDiscount(productDetail.getWholesalePriceDiscount());
            productToUpdate.setDefectiveQty(productDetail.getDefectiveQty());
            productToUpdate.setWeight(productDetail.getWeight());
            productToUpdate.setNote(productDetail.getNote());
            ProductDetail productVariantUpdated = productVariantRepo.save(productToUpdate);

            String logTitle = "Cập nhật thông tin sản phẩm: " + productVariantUpdated.getVariantName();
            Map<String, Object[]> logChanges = LogUtils.logChanges(productBeforeUpdate, productVariantUpdated);
            productHistoryService.save(logChanges, logTitle, productVariantUpdated.getProduct().getId(), productVariantUpdated.getId(), null);
            systemLogService.writeLogUpdate(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), mainObjectName, logTitle, logChanges);
            logger.info("Update productVariant success! {}", productVariantUpdated);

            return ProductVariantConvert.entityToDTO(productVariantUpdated);
        } catch (Exception e) {
            throw new AppException("Update productVariant fail! " + productDetail.toString(), e);
        }
    }

    @Override
    public String delete(Integer productVariantId) {
        Optional<ProductVariantDTO> productDetailToDelete = this.findById(productVariantId);
        if (productDetailToDelete.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            productVariantRepo.deleteById(productVariantId);
            systemLogService.writeLogDelete(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), mainObjectName, "Xóa biến thể sản phẩm", productDetailToDelete.get().getVariantName());
            logger.info("Delete productVariant success! {}", productDetailToDelete);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException("Delete productVariant fail! id=" + productVariantId, ex);
        }
    }

    @Override
    public boolean isProductVariantExists(int productId, int colorId, int sizeId, int fabricTypeId) {
        ProductDetail productDetail = productVariantRepo.findByColorAndSize(productId, colorId, sizeId, fabricTypeId);
        return ObjectUtils.isNotEmpty(productDetail);
    }

    @Override
    public List<ProductVariantTempDTO> findStorageHistory(Integer productVariantId) {
        List<ProductVariantTemp> storageHistory = productVariantTempRepo.findByProductVariantId(productVariantId);
        List<ProductVariantTempDTO> storageHistoryDTOs = ProductVariantTempDTO.convertToDTOs(storageHistory);
        if (ObjectUtils.isEmpty(storageHistoryDTOs)) {
            return List.of();
        }
        for (ProductVariantTempDTO tempDTO : storageHistoryDTOs) {
            String staff = "";
            String actionLabel = "";
            String changeQty = "";
            String branchName = "";
            if (tempDTO.getQuantity() > 0) {
                changeQty = "+ " + tempDTO.getQuantity();
            }
            if (tempDTO.getTicketImport() != null) {
                staff = tempDTO.getTicketImport().getImporter();
                for (TicketImportAction importAction : TicketImportAction.values()) {
                    if (importAction.name().equals(tempDTO.getAction()) ) {
                        actionLabel = importAction.getLabel();
                        break;
                    }
                }
            }
            if (tempDTO.getTicketExport() != null) {
                staff = tempDTO.getTicketExport().getExporter();
                for (TicketExportAction exportAction : TicketExportAction.values()) {
                    if (exportAction.name().equals(tempDTO.getAction()) ) {
                        actionLabel = exportAction.getLabel();
                        break;
                    }
                }
            }
            tempDTO.setStaff(staff);
            tempDTO.setAction(actionLabel);
            tempDTO.setChangeQty(changeQty);
            tempDTO.setStorageQty(tempDTO.getStorageQty());
            tempDTO.setBranchName(branchName);
        }
        return storageHistoryDTOs;
    }
}