package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.product.ProductDetailRepository;
import com.flowiee.pms.repository.product.ProductDetailTempRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.service.sales.TicketImportService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.converter.ProductVariantConvert;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantServiceImpl extends BaseService implements ProductVariantService {
    @Autowired
    private ProductDetailRepository productVariantRepo;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired @Lazy
    private TicketImportService ticketImportService;
    @Autowired
    private TicketExportService ticketExportService;
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
    public ProductVariantDTO save(ProductVariantDTO productVariantDTO) {
        try {
            ProductDetail pVariant = ProductVariantConvert.dtoToEntity(productVariantDTO);
            if (pVariant.getStorageQty() == null)
                pVariant.setStorageQty(0);
            if (pVariant.getSoldQty() == null)
                pVariant.setSoldQty(0);
            if (pVariant.getDefectiveQty() == null)
                pVariant.setDefectiveQty(0);
            pVariant.setStatus(AppConstants.PRODUCT_STATUS.A.name());
            pVariant.setVariantCode(productVariantDTO.getVariantCode() != null ? productVariantDTO.getVariantCode() : CommonUtils.genProductCode());
            ProductDetail productDetailSaved = productVariantRepo.save(pVariant);

            if (productDetailSaved.getStorageQty() > 0) {
                String initMessage = "Initialize storage quantity when create new products";

                TicketImport ticketImport = new TicketImport();
                ticketImport.setTitle("Initialize storage");
                ticketImport.setImporter(CommonUtils.getUserPrincipal().getUsername());
                ticketImport.setImportTime(LocalDateTime.now());
                ticketImport.setNote(initMessage);
                ticketImport.setStatus(AppConstants.TICKET_IM_STATUS.COMPLETED.name());
                ticketImport.setStorage(new Storage(productVariantDTO.getStorageIdInitStorageQty()));
                TicketImport ticketImportSaved = ticketImportService.save(ticketImport);

                ProductVariantTemp productVariantTemp = new ProductVariantTemp();
                productVariantTemp.setTicketImport(ticketImportSaved);
                productVariantTemp.setProductVariant(productDetailSaved);
                productVariantTemp.setQuantity(productDetailSaved.getStorageQty());
                productVariantTemp.setNote(initMessage);
                ProductVariantTemp productVariantTempSaved = productVariantTempRepo.save(productVariantTemp);
            }
            if (productDetailSaved.getSoldQty() > 0) {
                String initMessage = "Initialize storage quantity when create new products";

                TicketExport ticketExport = new TicketExport();
                ticketExport.setTitle("Initialize storage");
                ticketExport.setExporter(CommonUtils.getUserPrincipal().getUsername());
                ticketExport.setExportTime(LocalDateTime.now());
                ticketExport.setNote(initMessage);
                ticketExport.setStatus(AppConstants.TICKET_EX_STATUS.COMPLETED.name());
                ticketExport.setStorage(new Storage(productVariantDTO.getStorageIdInitStorageQty()));
                TicketExport ticketExportSaved = ticketExportService.save(ticketExport);

                ProductVariantTemp productVariantTemp = new ProductVariantTemp();
                productVariantTemp.setTicketExport(ticketExportSaved);
                productVariantTemp.setProductVariant(productDetailSaved);
                productVariantTemp.setQuantity(productDetailSaved.getStorageQty());
                productVariantTemp.setNote(initMessage);
                ProductVariantTemp productVariantTempSaved = productVariantTempRepo.save(productVariantTemp);
            }

            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), "Thêm mới biến thể sản phẩm: " + pVariant);
            logger.info("Insert productVariant success! {}", pVariant);
            return ProductVariantConvert.entityToDTO(productDetailSaved);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product variant"), ex);
        }
    }

    @Override
    public ProductVariantDTO update(ProductVariantDTO productDetail, Integer productVariantId) {
        Optional<ProductVariantDTO> productDetailBefore = this.findById(productVariantId);
        if (productDetailBefore.isEmpty()) {
            throw new BadRequestException();
        }
        try {
            productDetail.setId(productVariantId);
            ProductDetail productDetailUpdated = productVariantRepo.save(productDetail);
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), "Cập nhật biến thể sản phẩm: " + productDetailBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + productDetail);
            logger.info("Update productVariant success! {}", productDetail);
            return ProductVariantConvert.entityToDTO(productDetailUpdated);
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
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_PRD_U.name(), "Xóa biến thể sản phẩm: " + productDetailToDelete.toString());
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
}