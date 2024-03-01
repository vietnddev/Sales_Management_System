package com.flowiee.app.service.impl;

import com.flowiee.app.model.dto.PriceDTO;
import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.ProductHistory;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.entity.Price;
import com.flowiee.app.repository.PriceRepository;
import com.flowiee.app.service.PriceService;
import com.flowiee.app.service.ProductHistoryService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;

import com.flowiee.app.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    private static final String module = AppConstants.SYSTEM_MODULE.PRODUCT.name();

    @Autowired
    private PriceRepository priceRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductHistoryService productHistoryService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<Price> findAll() {
        return priceRepo.findAll();
    }

    @Override
    public List<PriceDTO> findPricesByProductVariant(int productVariantId) {
        List<PriceDTO> listPrice = PriceDTO.fromPrices(priceRepo.findPriceByProductVariant(productVariantId));
        if (!listPrice.isEmpty()) {
            for (PriceDTO p : listPrice) {
                if (AppConstants.PRICE_STATUS.A.name().equals(p.getStatus())) {
                    p.setStatus(AppConstants.PRICE_STATUS.A.getLabel());
                }
                if (AppConstants.PRICE_STATUS.I.name().equals(p.getStatus())) {
                    p.setStatus(AppConstants.PRICE_STATUS.I.getLabel());
                }
            }
        }
        return listPrice;
    }

    @Override
    public Price findById(Integer priceId) {
        Price p = priceRepo.findById(priceId).orElse(null);
        if (p != null) {
            if (AppConstants.PRICE_STATUS.A.name().equals(p.getStatus())) {
                p.setStatus(AppConstants.PRICE_STATUS.A.getLabel());
            }
            if (AppConstants.PRICE_STATUS.I.name().equals(p.getStatus())) {
                p.setStatus(AppConstants.PRICE_STATUS.I.getLabel());
            }
        }
        return p;
    }

    @Override
    public Price findGiaHienTai(int bienTheSanPhamId) {
        return priceRepo.findGiaBanHienTai(productService.findProductVariantById(bienTheSanPhamId), AppConstants.PRICE_STATUS.A.name());
    }

    @Override
    public Price save(Price price) {
        if (price == null) {
            throw new BadRequestException();
        }
        try {
            Price priceSaved = priceRepo.save(price);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_PRICE.name(), "Thêm mới giá sản phẩm: " + price.toString());
            logger.info("Insert price success! insertBy=" + CommonUtils.getCurrentAccountUsername());
            return priceSaved;
        } catch (Exception e) {
            logger.error("Insert price fail! price=" + price.toString());
            e.printStackTrace();
            throw new AppException();
        }
    }

    @Override
    public Price update(Price entity, Integer entityId) {
        return null;
    }

    @Transactional
    @Override
    public Price update(Price price, int bienTheSanPhamId, int priceId) {
        //Chuyển trạng thái giá hiện tại về false
        Price disableGiaCu = null;
        if (priceId > 0) {
            disableGiaCu = this.findById(priceId);
            disableGiaCu.setStatus(AppConstants.PRICE_STATUS.I.name());
            priceRepo.save(disableGiaCu);
        }
        //Thêm giá mới
        ProductVariant productVariant = productService.findProductVariantById(bienTheSanPhamId);
        price.setId(0);
        price.setProductVariant(productVariant);
        price.setType("S");
        price.setStatus(AppConstants.PRICE_STATUS.A.name());
        Price priceUpdated = priceRepo.save(price);
        //
        ProductHistory productHistory = new ProductHistory();
        productHistory.setTitle("Cập nhật giá bán");
        productHistory.setProduct(new Product(productVariant.getProduct().getId()));
        productHistory.setProductVariant(productVariant);
        productHistory.setFieldName(productVariant.getVariantName());
        productHistory.setOldValue(disableGiaCu != null ? disableGiaCu.getGiaBan().toString() : "-");
        productHistory.setNewValue(price.getGiaBan().toString());
        productHistoryService.save(productHistory);
        //Lưu log
        String content = "";
        if (disableGiaCu != null) {
            content = "Giá cũ:  " + disableGiaCu.getGiaBan();
        } else {
            content = "Giá cũ:  -";
        }
        String contentChange = "Giá mới: " + price.getGiaBan();
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_PRICE.name(), "Cập nhật giá sản phẩm: " + content, "Giá sau khi cập nhật: " + contentChange);
        logger.info("Update price success! updateBy=" + CommonUtils.getCurrentAccountUsername());
        return priceUpdated;
    }

    @Override
    public String delete(Integer priceId) {
        try {
            Price price = this.findById(priceId);
            priceRepo.deleteById(priceId);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_PRICE.name(), "Xóa giá sản phẩm: " + price.toString());
            logger.info("Delete price success! deleteBy=" + CommonUtils.getCurrentAccountUsername());
            return MessageUtils.DELETE_SUCCESS;
        } catch (Exception e) {
            logger.error("Delete price fail! priceId=" + priceId, e);
            throw new AppException();
        }
    }
}