package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.*;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.dto.PriceDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.repository.ProductVariantRepository;
import com.flowiee.app.service.PriceService;
import com.flowiee.app.service.ProductVariantService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {
    private static final Logger logger = LoggerFactory.getLogger(ProductVariantServiceImpl.class);
    private static final String module = SystemModule.PRODUCT.name();

    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<ProductVariant> findAll() {
        return this.extractDataQuery(productVariantRepository.findAll(null, null, null, null, null));
    }

    @Override
    public ProductVariant findById(Integer entityId) {
        return productVariantRepository.findById(entityId).orElse(null);
    }

    @Override
    public List<ProductVariantDTO> findAllProductVariantOfProduct(Integer productId) {
        List<ProductVariantDTO> listReturn = new ArrayList<>();
        List<ProductVariant> data = this.extractDataQuery(productVariantRepository.findAll(productId, null, null, null, null));
        for (ProductVariant productVariant : data) {
            ProductVariantDTO dataModel = ProductVariantDTO.fromProductVariant(productVariant);
            List<PriceDTO> listPriceOfProductVariant = priceService.findPricesByProductVariant(dataModel.getProductVariantId());
            PriceDTO price =  PriceDTO.fromPrice(priceService.findGiaHienTai(dataModel.getProductVariantId()));
            if (price != null) {
                dataModel.setPriceSellId(price.getId());
                dataModel.setPriceSellValue(Double.parseDouble(price.getGiaBan()));
            } else {
                dataModel.setPriceSellId(null);
                dataModel.setPriceSellValue(null);
            }
            dataModel.setListPrices(listPriceOfProductVariant);
            dataModel.setDiscountPercent(null);
            dataModel.setPriceMaxDiscount(null);
            dataModel.setPriceAfterDiscount(null);
            listReturn.add(dataModel);
        }
        return listReturn;
    }

    @Override
    public Double getGiaBan(int id) {
        if (priceService.findGiaHienTai(id) != null) {
            return priceService.findGiaHienTai(id).getGiaBan();
        }
        return null;
    }

    @Override
    public String save(ProductVariant productVariant) {
        try {
            String tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
//            if (productVariant.getTenBienThe().isEmpty()) {
//                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
//            } else {
//                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - " + productVariant.getTenBienThe() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
//            }
            productVariant.setTenBienThe(tenBienTheSanPham);
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Thêm mới biến thể sản phẩm: " + productVariant.toString());
            logger.info("Insert productVariant success! " + productVariant.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
        	logger.error("Insert productVariant fail! " + productVariant.toString(), e);
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer entityId) {
        ProductVariant productVariantToDelete = this.findById(entityId);
        try {
            productVariantRepository.deleteById(entityId);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Xóa biến thể sản phẩm: " + productVariantToDelete.toString());
            logger.info("Delete productVariant success! " + productVariantToDelete.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
        	logger.error("Delete productVariant fail! " + productVariantToDelete.toString(), e);
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(ProductVariant productVariant, Integer id) {
        ProductVariant productVariantBefore = this.findById(id);
        try {
            productVariant.setId(id);
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Cập nhật biến thể sản phẩm: " + productVariantBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + productVariant);
            logger.info("Update productVariant success! " + productVariant.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
        	logger.info("Update productVariant fail! " + productVariant.toString(), e);
            e.printStackTrace();            
        }
        return AppConstants.SERVICE_RESPONSE_FAIL;
    }

    @Override
    public String updateSoLuong(Integer soLuong, Integer id) {
        ProductVariant productVariant = this.findById(id);
        productVariant.setSoLuongKho(productVariant.getSoLuongKho() - soLuong);
        try {
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Cập nhật lại số lượng sản phẩm khi tạo đơn hàng");
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!");
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public List<ProductVariant> findByImportId(Integer ticketImportId) {
        return this.extractDataQuery(productVariantRepository.findAll(null, ticketImportId, null, null, null));
    }

    @Override
    public List<ProductVariant> findByFabricType(Integer fabricTypeId) {
        return this.extractDataQuery(productVariantRepository.findAll(null, null, null, null, fabricTypeId));
    }

    @Override
    public List<ProductVariant> findBySize(Integer sizeId) {
        return this.extractDataQuery(productVariantRepository.findAll(null, null, null, sizeId, null));
    }

    @Override
    public List<ProductVariant> findByColor(Integer colorId) {
        return this.extractDataQuery(productVariantRepository.findAll(null, null, colorId, null, null));
    }

    @Override
    public Integer findQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId) {
        return productVariantRepository.findQuantityBySizeOfEachColor(productId, colorId, sizeId);
    }

    @Override
    public Integer findTotalQtySell(Integer productId) {
        return productVariantRepository.findTotalQtySell(productId);
    }

    private List<ProductVariant> extractDataQuery(List<Object[]> objects) {
        List<ProductVariant> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            ProductVariant productVariant = new ProductVariant();
            productVariant.setProduct(new Product(Integer.parseInt(String.valueOf(data[0])), String.valueOf(data[1])));
            productVariant.setId(Integer.parseInt(String.valueOf(data[2])));
            productVariant.setMaSanPham(String.valueOf(data[3]));
            productVariant.setTenBienThe(String.valueOf(data[4]));
            productVariant.setColor(new Category(Integer.parseInt(String.valueOf(data[5])), String.valueOf(data[6])));
            productVariant.setSize(new Category(Integer.parseInt(String.valueOf(data[7])), String.valueOf(data[8])));
            productVariant.setFabricType( new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
            productVariant.setSoLuongKho(Integer.parseInt(String.valueOf(data[11])));
            productVariant.setSoLuongDaBan(Integer.parseInt(String.valueOf(data[12])));
            productVariant.setGarmentFactory(new GarmentFactory(Integer.parseInt(String.valueOf(data[13])), String.valueOf(data[14])));
            productVariant.setSupplier(new Supplier(Integer.parseInt(String.valueOf(data[15])), String.valueOf(data[16])));
            productVariant.setTicketImportGoods(new TicketImportGoods(Integer.parseInt(String.valueOf(data[17])), String.valueOf(data[18])));
            Integer priceId = data[19] != null ? Integer.parseInt(String.valueOf(data[19])) : null;
            Double priceSellValue = data[20] != null ? Double.parseDouble(String.valueOf(data[20])) : null;
            productVariant.setPrice(new Price(priceId, priceSellValue));
            productVariant.setTrangThai(String.valueOf(data[21]));
            dataResponse.add(productVariant);
        }
        return dataResponse;
    }
}