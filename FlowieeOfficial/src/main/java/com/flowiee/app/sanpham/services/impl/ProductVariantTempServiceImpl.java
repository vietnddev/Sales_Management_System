package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.ProductVariantTemp;
import com.flowiee.app.sanpham.repository.BienTheSanPhamTempRepository;
import com.flowiee.app.sanpham.services.PriceService;
import com.flowiee.app.sanpham.services.ProductVariantTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantTempServiceImpl implements ProductVariantTempService {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantTempServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private BienTheSanPhamTempRepository bienTheSanPhamTempRepository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<ProductVariantTemp> findAll() {
        return bienTheSanPhamTempRepository.findAll();
    }

    @Override
    public ProductVariantTemp findById(Integer entityId) {
        return bienTheSanPhamTempRepository.findById(entityId).orElse(null);
    }

    @Override
    public List<ProductVariantTemp> getListVariantOfProduct(int sanPhamId) {
        List<ProductVariantTemp> listReturn = new ArrayList<>();
        bienTheSanPhamTempRepository.findListBienTheOfsanPham(sanPhamId).forEach(bienTheSanPham -> {
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
    public String save(ProductVariantTemp bienTheSanPham) {
        if (bienTheSanPham.getLoaiMauSac() == null || bienTheSanPham.getLoaiKichCo() == null) {
            throw new BadRequestException();
        }
        if (bienTheSanPhamTempRepository.findByMauSacAndKichCo(bienTheSanPham.getSanPham().getId(),
                                                           bienTheSanPham.getLoaiMauSac().getId(),
                                                           bienTheSanPham.getLoaiKichCo().getId()) != null)
        {
            throw new DataExistsException();
        }
        try {
            String tenBienTheSanPham = "";
            if (bienTheSanPham.getTenBienThe().isEmpty()) {
                tenBienTheSanPham = bienTheSanPham.getSanPham().getTenSanPham() + " - Size " + bienTheSanPham.getLoaiKichCo().getTenLoai() + " - Màu " + bienTheSanPham.getLoaiMauSac().getTenLoai();
            } else {
                tenBienTheSanPham = bienTheSanPham.getSanPham().getTenSanPham() + " - " + bienTheSanPham.getTenBienThe() + " - Size " + bienTheSanPham.getLoaiKichCo().getTenLoai() + " - Màu " + bienTheSanPham.getLoaiMauSac().getTenLoai();
            }
            bienTheSanPham.setTenBienThe(tenBienTheSanPham);
            bienTheSanPhamTempRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Thêm mới biến thể sản phẩm: " + bienTheSanPham.toString());
            logger.info(ProductVariantTempServiceImpl.class.getName() + ": Thêm mới biến thể sản phẩm " + bienTheSanPham.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer entityId) {
        ProductVariantTemp bienTheSanPhamToDelete = this.findById(entityId);
        if (bienTheSanPhamToDelete == null) {
            throw new NotFoundException();
        }
        try {
            bienTheSanPhamTempRepository.deleteById(entityId);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Xóa biến thể sản phẩm: " + bienTheSanPhamToDelete.toString());
            logger.info(ProductVariantTempServiceImpl.class.getName() + ": Xóa biến thể sản phẩm " + bienTheSanPhamToDelete.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(ProductVariantTemp bienTheSanPham, Integer id) {
        ProductVariantTemp bienTheSanPhamBefore = this.findById(id);
        if (this.findById(id) == null) {
            throw new NotFoundException();
        }
        try {
            bienTheSanPham.setId(id);
            bienTheSanPhamTempRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật biến thể sản phẩm: " + bienTheSanPhamBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + bienTheSanPham);
            logger.info(ProductVariantTempServiceImpl.class.getName() + ": Cập nhật biến thể sản phẩm " + bienTheSanPhamBefore.toString());
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
        ProductVariantTemp bienTheSanPham = this.findById(id);
        if (bienTheSanPham == null) {
            throw new BadRequestException();
        }
        bienTheSanPham.setSoLuongKho(bienTheSanPham.getSoLuongKho() - soLuong);
        try {
            bienTheSanPhamTempRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật lại số lượng sản phẩm khi tạo đơn hàng");
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!", bienTheSanPham);
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public List<ProductVariantTemp> findByImportId(Integer importId) {
        List<ProductVariantTemp> listData = new ArrayList<>();
        if (importId != null && importId > 0) {
            listData = bienTheSanPhamTempRepository.findByImportId(importId);
        }
        return listData;
    }
}