package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductPrice;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.dto.ProductPriceDTO;
import com.flowiee.pms.repository.product.ProductPriceRepository;
import com.flowiee.pms.utils.ChangeLog;
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
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.utils.converter.ProductVariantConvert;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductVariantServiceImpl extends BaseService implements ProductVariantService {
    TicketImportService mvTicketImportService;
    TicketExportService mvTicketExportService;
    ProductHistoryService mvProductHistoryService;
    ProductPriceRepository mvProductPriceRepository;
    ProductDetailRepository mvProductVariantRepository;
    ProductDetailTempRepository mvProductVariantTempRepository;

    BigDecimal ZERO = BigDecimal.ZERO;

    @Override
    public List<ProductVariantDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<ProductVariantDTO> findAll(int pageSize, int pageNum, Long pProductId, Long pTicketImport, Long pColor, Long pSize, Long pFabricType, Boolean pAvailableForSales) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize);
        }
        List<ProductDetail> productVariants = mvProductVariantRepository.findAll(pProductId, pColor, pSize, pFabricType, pAvailableForSales, pageable);
        List<ProductVariantDTO> productVariantDTOs = ProductVariantConvert.entitiesToDTOs(productVariants);
        for (ProductVariantDTO dto : productVariantDTOs) {
            //ProductPrice productPrice = mvProductPriceRepository.findPricePresent(null, dto.getId());
            setPriceInfo(dto, dto.getVariantPrice());
        }
        return new PageImpl<>(productVariantDTOs, pageable, productVariants.size());
    }

    @Override
    public Optional<ProductVariantDTO> findById(Long pProductVariantId) {
        Optional<ProductDetail> productVariant = mvProductVariantRepository.findById(pProductVariantId);
        if (productVariant.isPresent()) {
            ProductVariantDTO dto = ProductVariantConvert.entityToDTO(productVariant.get());
            //ProductPrice productPrice = mvProductPriceRepository.findPricePresent(null, dto.getId());
            setPriceInfo(dto, dto.getVariantPrice());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public ProductVariantDTO save(ProductVariantDTO inputDTO) {
        try {
            ProductDetail pVariant = ProductVariantConvert.dtoToEntity(inputDTO);
            String lvVariantCode = ObjectUtils.isNotEmpty(inputDTO.getVariantCode()) ? inputDTO.getVariantCode() : CommonUtils.genProductCode();
            int lvSoldQty = pVariant.getSoldQty() != null ? pVariant.getSoldQty() : 0;
            int lvStorageQty = pVariant.getStorageQty() != null ? pVariant.getStorageQty() : 0;
            int lvDefectiveQty = pVariant.getDefectiveQty() != null ? pVariant.getDefectiveQty() : 0;

            pVariant.setSoldQty(lvSoldQty);
            pVariant.setStorageQty(lvStorageQty);
            pVariant.setDefectiveQty(lvDefectiveQty);
            pVariant.setStatus(ProductStatus.A.name());
            pVariant.setVariantCode(lvVariantCode);
            ProductDetail productDetailSaved = mvProductVariantRepository.save(pVariant);

            ProductPriceDTO priceDTO = inputDTO.getPrice();
            BigDecimal lvRetailPrice = priceDTO.getRetailPrice() != null ? priceDTO.getRetailPrice() : ZERO;
            BigDecimal lvRetailPriceDiscount = priceDTO.getRetailPriceDiscount() != null ? priceDTO.getRetailPriceDiscount() : priceDTO.getRetailPrice();
            BigDecimal lvWholesalePrice = priceDTO.getWholesalePrice() != null ? priceDTO.getWholesalePrice() : ZERO;
            BigDecimal lvWholesalePriceDiscount = priceDTO.getWholesalePriceDiscount() != null ? priceDTO.getWholesalePriceDiscount() : priceDTO.getWholesalePrice();
            BigDecimal lvPurchasePrice = priceDTO.getPurchasePrice() != null ? priceDTO.getPurchasePrice() : ZERO;
            BigDecimal lvCostPrice = priceDTO.getCostPrice() != null ? priceDTO.getCostPrice() : ZERO;
            mvProductPriceRepository.save(ProductPrice.builder()
                    .productVariant(productDetailSaved)
                    .retailPrice(lvRetailPrice)
                    .retailPriceDiscount(lvRetailPriceDiscount)
                    .wholesalePrice(lvWholesalePrice)
                    .wholesalePriceDiscount(lvWholesalePriceDiscount)
                    .purchasePrice(lvPurchasePrice)
                    .costPrice(lvCostPrice)
                    .state(ProductPrice.STATE_ACTIVE)
                    .build());

            if (productDetailSaved.getStorageQty() > 0) {
                String initMessage = "Initialize storage quantity when create new products";

                TicketImport ticketImport = TicketImport.builder()
                    .title("Initialize storage")
                    .importer(CommonUtils.getUserPrincipal().getUsername())
                    .importTime(LocalDateTime.now())
                    .note(initMessage)
                    .status(TicketImportStatus.COMPLETED.name())
                    .storage(new Storage(inputDTO.getStorageIdInitStorageQty())).build();
                TicketImport ticketImportSaved = mvTicketImportService.save(ticketImport);

                ProductVariantTemp productVariantTemp = ProductVariantTemp.builder()
                    .ticketImport(ticketImportSaved)
                    .productVariant(productDetailSaved)
                    .quantity(productDetailSaved.getStorageQty())
                    .note(initMessage).build();
                mvProductVariantTempRepository.save(productVariantTemp);
            }
            if (productDetailSaved.getSoldQty() > 0) {
                String initMessage = "Initialize storage quantity when create new products";

                TicketExport ticketExport = TicketExport.builder()
                    .title("Initialize storage")
                    .exporter(CommonUtils.getUserPrincipal().getUsername())
                    .exportTime(LocalDateTime.now())
                    .note(initMessage)
                    .status(TicketExportStatus.COMPLETED.name())
                    .storage(new Storage(inputDTO.getStorageIdInitStorageQty())).build();
                TicketExport ticketExportSaved = mvTicketExportService.save(ticketExport);

                ProductVariantTemp productVariantTemp = ProductVariantTemp.builder()
                    .ticketExport(ticketExportSaved)
                    .productVariant(productDetailSaved)
                    .quantity(productDetailSaved.getStorageQty())
                    .note(initMessage).build();
                mvProductVariantTempRepository.save(productVariantTemp);
            }

            systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductVariant, "Thêm mới biến thể sản phẩm", pVariant.toStringInsert());
            logger.info("Insert productVariant success! {}", pVariant);
            return ProductVariantConvert.entityToDTO(productDetailSaved);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "product variant"), ex);
        }
    }

    @Transactional
    @Override
    public ProductVariantDTO update(ProductVariantDTO productDetail, Long productVariantId) {
        Optional<ProductDetail> productVariantOptional = mvProductVariantRepository.findById(productVariantId);
        if (productVariantOptional.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            //Product variant info
            ProductDetail productBeforeUpdate = ObjectUtils.clone(productVariantOptional.get());
            ProductDetail productToUpdate = productVariantOptional.get();
            productToUpdate.setVariantName(productDetail.getVariantName());
            productToUpdate.setDefectiveQty(productDetail.getDefectiveQty());
            productToUpdate.setWeight(productDetail.getWeight());
            productToUpdate.setNote(productDetail.getNote());
            ProductDetail productVariantUpdated = mvProductVariantRepository.save(productToUpdate);

            //Price
            ProductPrice productVariantPricePresent = mvProductPriceRepository.findPricePresent(null, productVariantUpdated.getId());
            if (productVariantPricePresent != null) {
                productVariantPricePresent.setState(ProductPrice.STATE_INACTIVE);
                mvProductPriceRepository.save(productVariantPricePresent);
            }

            ProductPriceDTO priceDTO = productDetail.getPrice();
            BigDecimal lvRetailPrice = priceDTO.getRetailPrice() != null ? priceDTO.getRetailPrice() : ZERO;
            BigDecimal lvRetailPriceDiscount = priceDTO.getRetailPriceDiscount() != null ? priceDTO.getRetailPriceDiscount() : priceDTO.getRetailPrice();
            BigDecimal lvWholesalePrice = priceDTO.getWholesalePrice() != null ? priceDTO.getWholesalePrice() : ZERO;
            BigDecimal lvWholesalePriceDiscount = priceDTO.getWholesalePriceDiscount() != null ? priceDTO.getWholesalePriceDiscount() : priceDTO.getWholesalePrice();
            BigDecimal lvPurchasePrice = priceDTO.getPurchasePrice() != null ? priceDTO.getPurchasePrice() : ZERO;
            BigDecimal lvCostPrice = priceDTO.getCostPrice() != null ? priceDTO.getCostPrice() : ZERO;
            mvProductPriceRepository.save(ProductPrice.builder()
                    .productVariant(productVariantUpdated)
                    .retailPrice(lvRetailPrice)
                    .retailPriceDiscount(lvRetailPriceDiscount)
                    .wholesalePrice(lvWholesalePrice)
                    .wholesalePriceDiscount(lvWholesalePriceDiscount)
                    .purchasePrice(lvPurchasePrice)
                    .costPrice(lvCostPrice)
                    .state(ProductPrice.STATE_ACTIVE)
                    .build());

            //Log
            String logTitle = "Cập nhật thông tin sản phẩm: " + productVariantUpdated.getVariantName();
            ChangeLog changeLog = new ChangeLog(productBeforeUpdate, productVariantUpdated);
            mvProductHistoryService.save(changeLog.getLogChanges(), logTitle, productVariantUpdated.getProduct().getId(), productVariantUpdated.getId(), null);
            systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductVariant, logTitle, changeLog.getOldValues(), changeLog.getNewValues());
            logger.info("Update productVariant success! {}", productVariantUpdated);

            return ProductVariantConvert.entityToDTO(productVariantUpdated);
        } catch (Exception e) {
            throw new AppException("Update productVariant fail! " + productDetail.toString(), e);
        }
    }

    @Override
    public String delete(Long productVariantId) {
        Optional<ProductVariantDTO> productDetailToDelete = this.findById(productVariantId);
        if (productDetailToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Product variant not found!");
        }
        try {
            mvProductVariantRepository.deleteById(productVariantId);
        } catch (ConstraintViolationException ex) {
            throw new DataInUseException("Không thể xóa sản phẩm đã được sử dụng!", ex);
        }
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_PRD_U, MasterObject.ProductVariant, "Xóa biến thể sản phẩm", productDetailToDelete.get().getVariantName());
        logger.info("Delete productVariant success! {}", productDetailToDelete);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public boolean isProductVariantExists(long productId, long colorId, long sizeId, long fabricTypeId) {
        ProductDetail productDetail = mvProductVariantRepository.findByColorAndSize(productId, colorId, sizeId, fabricTypeId);
        return ObjectUtils.isNotEmpty(productDetail);
    }

    @Override
    public List<ProductVariantTempDTO> findStorageHistory(Long productVariantId) {
        List<ProductVariantTemp> storageHistory = mvProductVariantTempRepository.findByProductVariantId(productVariantId);
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

    private ProductVariantDTO setPriceInfo(ProductVariantDTO dto, ProductPrice productPrice) {
        if (dto != null) {
            if (productPrice != null) {
                dto.setPrice(ProductPriceDTO.builder()
                        .retailPrice(productPrice.getRetailPrice())
                        .retailPriceDiscount(productPrice.getRetailPriceDiscount())
                        .wholesalePrice(productPrice.getWholesalePrice())
                        .wholesalePriceDiscount(productPrice.getWholesalePriceDiscount())
                        .purchasePrice(productPrice.getPurchasePrice())
                        .costPrice(productPrice.getCostPrice())
                        .lastUpdated(productPrice.getLastUpdatedAt())
                        .build());
            } else {
                dto.setPrice(new ProductPriceDTO());
            }
        }
        return dto;
    }
}