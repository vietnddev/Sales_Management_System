package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.*;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.model.dto.VoucherInfoDTO;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.repository.product.ProductRepository;
import com.flowiee.pms.service.product.*;
import com.flowiee.pms.service.sales.VoucherApplyService;
import com.flowiee.pms.service.sales.VoucherService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ProductInfoServiceImpl.class);
    private static final String mvModule = MODULE.PRODUCT.name();

    @Autowired
    private ProductRepository productsRepo;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private ProductHistoryService productHistoryService;
    @Autowired
    private VoucherService voucherInfoService;
    @Autowired
    private VoucherApplyService voucherApplyService;
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ProductStatisticsService productStatisticsService;
    @Autowired
    private ProductImageService productImageService;

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
        List<ProductDTO> productDTOs = ProductDTO.fromProducts(products);
        this.setImageActiveAndLoadVoucherApply(productDTOs);
        this.setInfoVariantOfProduct(productDTOs);
        return new PageImpl<>(productDTOs, pageable, products.getTotalElements());
    }

    @Override
    public List<Product> findProductsIdAndProductName() {
        List<Product> products = new ArrayList<>();
        for (Object[] objects : productsRepo.findIdAndName(AppConstants.PRODUCT_STATUS.A.name())) {
            products.add(new Product(Integer.parseInt(String.valueOf(objects[0])), String.valueOf(objects[1])));
        }
        return products;
    }

    @Override
    public Optional<ProductDTO> findById(Integer id) {
        Optional<Product> product = productsRepo.findById(id);
        return product.map(p -> Optional.of(ProductDTO.fromProduct(p))).orElse(null);
    }

    @Override
    public ProductDTO save(ProductDTO product) {
        try {
            product.setCreatedBy(CommonUtils.getUserPrincipal().getId());
            product.setDescription(product.getDescription() != null ? product.getDescription() : "");
            product.setStatus(AppConstants.PRODUCT_STATUS.I.name());
            Product productSaved = productsRepo.save(product);
            systemLogService.writeLog(mvModule, ACTION.PRO_PRD_C.name(), "Thêm mới sản phẩm: " + product);
            logger.info("Insert product success! {}", product);
            return ProductDTO.fromProduct(productSaved);
        } catch (RuntimeException ex) {
            throw new AppException("Insert product fail!", ex);
        }
    }

    @Transactional
    @Override
    public ProductDTO update(ProductDTO productToUpdate, Integer productId) {
        if (productToUpdate.getDescription().isEmpty()) {
            productToUpdate.setDescription("-");
        }
        Optional<ProductDTO> productBefore = this.findById(productId);
        if (productBefore.isEmpty()) {
            throw new BadRequestException();
        }
        productBefore.get().compareTo(productToUpdate).forEach((key, value) -> {
            ProductHistory productHistory = new ProductHistory();
            productHistory.setTitle("Update product");
            productHistory.setProduct(new Product(productId));
            productHistory.setField(key);
            productHistory.setOldValue(value.substring(0, value.indexOf("#")));
            productHistory.setNewValue(value.substring(value.indexOf("#") + 1));
            productHistoryService.save(productHistory);
        });

        productToUpdate.setId(productId);
        productToUpdate.setLastUpdatedBy(CommonUtils.getUserPrincipal().getUsername());
        Product productUpdated = productsRepo.save(productToUpdate);
        String noiDungLog = "";
        String noiDungLogUpdate = "";
        if (productBefore.get().toString().length() > 1950) {
            noiDungLog = productBefore.get().toString().substring(0, 1950);
        } else {
            noiDungLog = productBefore.get().toString();
        }
        if (productToUpdate.toString().length() > 1950) {
            noiDungLogUpdate = productToUpdate.toString().substring(0, 1950);
        } else {
            noiDungLogUpdate = productToUpdate.toString();
        }
        systemLogService.writeLog(mvModule, ACTION.PRO_PRD_U.name(), "Cập nhật sản phẩm: " + noiDungLog, "Sản phẩm sau khi cập nhật: " + noiDungLogUpdate);
        logger.info("Update product success! productId={}", productId);
        return ProductDTO.fromProduct(productUpdated);
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        try {
            Optional<ProductDTO> productToDelete = this.findById(id);
            if (productToDelete.isEmpty()) {
                throw new BadRequestException();
            }
            if (productInUse(id)) {
                throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
            }
            productsRepo.deleteById(id);
            systemLogService.writeLog(mvModule, ACTION.PRO_PRD_D.name(), "Xóa sản phẩm: " + productToDelete.toString());
            logger.info("Delete product success! productId={}", id);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            throw new AppException("Delete product fail! productId=" + id, ex);
        }
    }

    @Override
    public boolean productInUse(Integer productId) throws RuntimeException {
        return !productVariantService.findAll().isEmpty();
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
            voucherApplyService.findByProductId(p.getId()).forEach(voucherApplyDTO -> {
                listVoucherInfoId.add(voucherApplyDTO.getVoucherInfoId());
            });
            if (!listVoucherInfoId.isEmpty()) {
                List<VoucherInfoDTO> voucherInfoDTOs = voucherInfoService.findAll(-1, -1, listVoucherInfoId, null, null, null, AppConstants.VOUCHER_STATUS.A.name()).getContent();
                p.setListVoucherInfoApply(voucherInfoDTOs);
            }
        }
    }

    private void setInfoVariantOfProduct(List<ProductDTO> products) {
        if (products != null) {
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
}