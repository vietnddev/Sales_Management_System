package com.flowiee.app.service.impl;

import com.flowiee.app.model.role.SanPhamAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.Price;
import com.flowiee.app.repository.PriceRepository;
import com.flowiee.app.service.PriceService;
import com.flowiee.app.service.ProductVariantService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {
    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    @Override
    public List<Price> findPricesByProductVariant(int bienTheSanPhamId) {
        return priceRepository.findPricesByProductVariant(productVariantService.findById(bienTheSanPhamId));
    }

    @Override
    public Price findById(Integer priceId) {
        return priceRepository.findById(priceId).orElse(null);
    }

    @Override
    public Double findGiaHienTai(int bienTheSanPhamId) {
        return priceRepository.findGiaBanHienTai(productVariantService.findById(bienTheSanPhamId), AppConstants.PRICE_STATUS.A.name());
    }

    @Override
    public String save(Price price) {
        if (price == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        try {
            priceRepository.save(price);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Thêm mới giá sản phẩm: " + price.toString());
            logger.info(PriceServiceImpl.class.getName() + ": Thêm mới giá sản phẩm " + price.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(Price entity, Integer entityId) {
        return null;
    }

    @Override
    public String update(Price price, int bienTheSanPhamId, int priceId) {
        try {
            //Chuyển trạng thái giá hiện tại về false
            Price disableGiaCu = this.findById(priceId);
            disableGiaCu.setStatus(AppConstants.PRICE_STATUS.I.name());
            priceRepository.save(disableGiaCu);
            //Thêm giá mới
            price.setId(0);
            price.setProductVariant(productVariantService.findById(bienTheSanPhamId));
            price.setStatus(AppConstants.PRICE_STATUS.A.name());
            priceRepository.save(price);
            //Lưu log
            String noiDung = "Giá cũ:  " + disableGiaCu.getGiaBan();
            String noiDungCapNhat = "Giá mới: " + price.getGiaBan();
            systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Cập nhật giá sản phẩm: " + noiDung.toString(), "Giá sau khi cập nhật: " + noiDungCapNhat.toString());
            logger.info(PriceServiceImpl.class.getName() + ": Cập nhật giá sản phẩm " + price.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer priceId) {
        Price price = this.findById(priceId);
        priceRepository.deleteById(priceId);
        systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Xóa giá sản phẩm: " + price.toString());
        logger.info(PriceServiceImpl.class.getName() + ": Xóa giá sản phẩm " + price.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}