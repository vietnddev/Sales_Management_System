package com.flowiee.app.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.product.Price;
import com.flowiee.app.repository.product.PriceRepository;
import com.flowiee.app.service.product.PriceService;
import com.flowiee.app.service.product.ProductVariantService;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.SystemLogService;

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
    private AccountService accountService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    @Override
    public List<Price> findByBienTheSanPhamId(int bienTheSanPhamId) {
        return priceRepository.findListGiaBanOfSP(productVariantService.findById(bienTheSanPhamId));
    }

    @Override
    public Price findById(int id) {
        return priceRepository.findById(id).orElse(null);
    }

    @Override
    public Double findGiaHienTai(int bienTheSanPhamId) {
        return priceRepository.findGiaBanHienTai(productVariantService.findById(bienTheSanPhamId), true);
    }

    @Override
    public Price findGiaHienTaiModel(int bienTheSanPhamId) {
        return priceRepository.findGiaBanHienTaiModel(productVariantService.findById(bienTheSanPhamId), true);
    }

    @Override
    public String save(Price price) {
        if (price == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        try {
            priceRepository.save(price);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Thêm mới giá sản phẩm: " + price.toString());
            logger.info(PriceServiceImpl.class.getName() + ": Thêm mới giá sản phẩm " + price.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(Price price, int bienTheSanPhamId, int giaSanPhamId) {
        try {
            if (bienTheSanPhamId <= 0 || productVariantService.findById(bienTheSanPhamId) == null) {
                throw new NotFoundException();
            }
            if (giaSanPhamId <= 0 || this.findById(giaSanPhamId) == null) {
                throw new NotFoundException();
            }
            //Chuyển trạng thái giá hiện tại về false
            Price disableGiaCu = this.findById(giaSanPhamId);
            disableGiaCu.setTrangThai(false);
            priceRepository.save(disableGiaCu);
            //Thêm giá mới
            price.setId(0);
            price.setProductVariant(productVariantService.findById(bienTheSanPhamId));
            price.setTrangThai(true);
            priceRepository.save(price);
            //Lưu log
            String noiDung = "Giá cũ:  " + disableGiaCu.getGiaBan();
            String noiDungCapNhat = "Giá mới: " + price.getGiaBan();
            systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Cập nhật giá sản phẩm: " + noiDung.toString(), "Giá sau khi cập nhật: " + noiDungCapNhat.toString());
            logger.info(PriceServiceImpl.class.getName() + ": Cập nhật giá sản phẩm " + price.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }
    @Override
    public String delete(int id) {
        Price price = this.findById(id);
        if (id <= 0 || price == null) {
            throw new NotFoundException();
        }
        priceRepository.deleteById(id);
        systemLogService.writeLog(module, SanPhamAction.UPDATE_PRICE_SANPHAM.name(), "Xóa giá sản phẩm: " + price.toString());
        logger.info(PriceServiceImpl.class.getName() + ": Xóa giá sản phẩm " + price.toString());
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }
}