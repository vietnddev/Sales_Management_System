package com.flowiee.app.service.impl;

import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.ProductVariantTemp;
import com.flowiee.app.repository.ProductVariantTempRepository;
import com.flowiee.app.service.ProductVariantTempService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantTempServiceImpl implements ProductVariantTempService {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantTempServiceImpl.class);
    private static final String module = SystemModule.PRODUCT.name();

    @Autowired
    private ProductVariantTempRepository productVariantTempRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<ProductVariantTemp> findAll() {
        return productVariantTempRepository.findAll();
    }

    @Override
    public ProductVariantTemp findById(Integer entityId) {
        return productVariantTempRepository.findById(entityId).orElse(null);
    }

    @Override
    public String save(ProductVariantTemp bienTheSanPham) {
        try {
            String tenBienTheSanPham = "";
            if (bienTheSanPham.getTenBienThe().isEmpty()) {
                tenBienTheSanPham = bienTheSanPham.getProduct().getTenSanPham() + " - Size " + bienTheSanPham.getLoaiKichCo().getName() + " - Màu " + bienTheSanPham.getLoaiMauSac().getName();
            } else {
                tenBienTheSanPham = bienTheSanPham.getProduct().getTenSanPham() + " - " + bienTheSanPham.getTenBienThe() + " - Size " + bienTheSanPham.getLoaiKichCo().getName() + " - Màu " + bienTheSanPham.getLoaiMauSac().getName();
            }
            bienTheSanPham.setTenBienThe(tenBienTheSanPham);
            productVariantTempRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Thêm mới biến thể sản phẩm: " + bienTheSanPham.toString());
            logger.info(ProductVariantTempServiceImpl.class.getName() + ": Thêm mới biến thể sản phẩm " + bienTheSanPham.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer entityId) {
        ProductVariantTemp bienTheSanPhamToDelete = this.findById(entityId);
        try {
            productVariantTempRepository.deleteById(entityId);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Xóa biến thể sản phẩm: " + bienTheSanPhamToDelete.toString());
            logger.info(ProductVariantTempServiceImpl.class.getName() + ": Xóa biến thể sản phẩm " + bienTheSanPhamToDelete.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(ProductVariantTemp bienTheSanPham, Integer id) {
        ProductVariantTemp bienTheSanPhamBefore = this.findById(id);
        try {
            bienTheSanPham.setId(id);
            productVariantTempRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Cập nhật biến thể sản phẩm: " + bienTheSanPhamBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + bienTheSanPham);
            logger.info(ProductVariantTempServiceImpl.class.getName() + ": Cập nhật biến thể sản phẩm " + bienTheSanPhamBefore.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppConstants.SERVICE_RESPONSE_FAIL;
    }

    @Override
    public String updateSoLuong(Integer soLuong, Integer id) {
        ProductVariantTemp bienTheSanPham = this.findById(id);
        bienTheSanPham.setSoLuongKho(bienTheSanPham.getSoLuongKho() - soLuong);
        try {
            productVariantTempRepository.save(bienTheSanPham);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Cập nhật lại số lượng sản phẩm khi tạo đơn hàng");
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!", bienTheSanPham);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public List<ProductVariantTemp> findByImportId(Integer importId) {
        List<ProductVariantTemp> listData = new ArrayList<>();
        if (importId != null && importId > 0) {
            listData = productVariantTempRepository.findByImportId(importId);
        }
        return listData;
    }

	@Override
	public ProductVariantTemp findProductVariantInGoodsImport(Integer importId, Integer productVariantId) {
		return productVariantTempRepository.findProductVariantInGoodsImport(importId, productVariantId);
	}
}