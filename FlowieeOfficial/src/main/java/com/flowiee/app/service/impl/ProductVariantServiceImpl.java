package com.flowiee.app.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.product.ProductVariant;
import com.flowiee.app.repository.product.ProductVariantRepository;
import com.flowiee.app.service.product.PriceService;
import com.flowiee.app.service.product.ProductVariantService;
import com.flowiee.app.service.system.SystemLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<ProductVariant> findAll() {
        return productVariantRepository.findAll();
    }

    @Override
    public ProductVariant findById(Integer entityId) {
        return productVariantRepository.findById(entityId).orElse(null);
    }

    @Override
    public List<ProductVariant> getListVariantOfProduct(int sanPhamId) {
        List<ProductVariant> listReturn = new ArrayList<>();
        productVariantRepository.findListBienTheOfsanPham(sanPhamId).forEach(bienTheSanPham -> {
            bienTheSanPham.setPrice(priceService.findGiaHienTaiModel(bienTheSanPham.getId()));
            listReturn.add(bienTheSanPham);
        });
        return listReturn;
    }

    @Override
    public Double getGiaBan(int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        return priceService.findGiaHienTai(id);
    }

    @Override
    public String save(ProductVariant productVariant) {
        if (productVariant.getLoaiMauSac() == null || productVariant.getLoaiKichCo() == null) {
            throw new BadRequestException();
        }
        if (productVariantRepository.findByMauSacAndKichCo(productVariant.getProduct().getId(),
                                                           productVariant.getLoaiMauSac().getId(),
                                                           productVariant.getLoaiKichCo().getId()) != null)
        {
            throw new DataExistsException();
        }
        try {
            String tenBienTheSanPham = "";
            if (productVariant.getTenBienThe().isEmpty()) {
                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - Size " + productVariant.getLoaiKichCo().getTenLoai() + " - Màu " + productVariant.getLoaiMauSac().getTenLoai();
            } else {
                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - " + productVariant.getTenBienThe() + " - Size " + productVariant.getLoaiKichCo().getTenLoai() + " - Màu " + productVariant.getLoaiMauSac().getTenLoai();
            }
            productVariant.setTenBienThe(tenBienTheSanPham);
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Thêm mới biến thể sản phẩm: " + productVariant.toString());
            logger.info(ProductVariantServiceImpl.class.getName() + ": Thêm mới biến thể sản phẩm " + productVariant.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer entityId) {
        ProductVariant productVariantToDelete = this.findById(entityId);
        if (productVariantToDelete == null) {
            throw new NotFoundException();
        }
        try {
            productVariantRepository.deleteById(entityId);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Xóa biến thể sản phẩm: " + productVariantToDelete.toString());
            logger.info(ProductVariantServiceImpl.class.getName() + ": Xóa biến thể sản phẩm " + productVariantToDelete.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(ProductVariant productVariant, Integer id) {
        ProductVariant productVariantBefore = this.findById(id);
        if (this.findById(id) == null) {
            throw new NotFoundException();
        }
        try {
            productVariant.setId(id);
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật biến thể sản phẩm: " + productVariantBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + productVariant);
            logger.info(ProductVariantServiceImpl.class.getName() + ": Cập nhật biến thể sản phẩm " + productVariantBefore.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TagName.SERVICE_RESPONSE_FAIL;
    }

    @Override
    public String updateSoLuong(Integer soLuong, Integer id) {
        if (id < 0) {
            throw new BadRequestException();
        }
        ProductVariant productVariant = this.findById(id);
        if (productVariant == null) {
            throw new BadRequestException();
        }
        productVariant.setSoLuongKho(productVariant.getSoLuongKho() - soLuong);
        try {
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật lại số lượng sản phẩm khi tạo đơn hàng");
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!", productVariant);
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public List<ProductVariant> findByImportId(Integer importId) {
        List<ProductVariant> listData = new ArrayList<>();
        if (importId != null && importId > 0) {
            listData = productVariantRepository.findByImportId(importId);
        }
        return listData;
    }
}