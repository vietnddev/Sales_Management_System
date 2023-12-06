package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.*;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ProductVariant> findAll() {
        return this.findData(null, null);
    }

    @Override
    public ProductVariant findById(Integer entityId) {
        return productVariantRepository.findById(entityId).orElse(null);
    }

    @Override
    public List<ProductVariantDTO> findAllProductVariantOfProduct(Integer productId) {
        List<ProductVariantDTO> listReturn = new ArrayList<>();
        for (ProductVariant productVariant : this.findData(AppConstants.PRODUCT, String.valueOf(productId))) {
            ProductVariantDTO dataModel = ProductVariantDTO.fromProductVariant(productVariant);
            dataModel.setListPrices(priceService.findPricesByProductVariant(dataModel.getProductVariantId()));
            listReturn.add(dataModel);
        }
        return listReturn;
    }

    @Override
    public Double getGiaBan(int id) {
        return priceService.findGiaHienTai(id);
    }

    @Override
    public String save(ProductVariant productVariant) {
        try {
            String tenBienTheSanPham = "";
            if (productVariant.getTenBienThe().isEmpty()) {
                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
            } else {
                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - " + productVariant.getTenBienThe() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
            }
            productVariant.setTenBienThe(tenBienTheSanPham);
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Thêm mới biến thể sản phẩm: " + productVariant.toString());
            logger.info(ProductVariantServiceImpl.class.getName() + ": Thêm mới biến thể sản phẩm " + productVariant.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
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
            logger.info(ProductVariantServiceImpl.class.getName() + ": Xóa biến thể sản phẩm " + productVariantToDelete.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
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
            logger.info(ProductVariantServiceImpl.class.getName() + ": Cập nhật biến thể sản phẩm " + productVariantBefore.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
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
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!", productVariant);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public List<ProductVariant> findByImportId(Integer ticketImportId) {
        return findData(AppConstants.TICKETIMPORT, String.valueOf(ticketImportId));
    }

    @Override
    public List<ProductVariant> findByFabricType(Integer fabricTypeId) {
        return findData(AppConstants.FABRICTYPE, String.valueOf(fabricTypeId));
    }

    @Override
    public List<ProductVariant> findBySize(Integer sizeId) {
        return findData(AppConstants.SIZE, String.valueOf(sizeId));
    }

    @Override
    public List<ProductVariant> findByColor(Integer colorId) {
        return findData(AppConstants.COLOR, String.valueOf(colorId));
    }

    @SuppressWarnings("unchecked")
	private List<ProductVariant> findData(String where, String valueWhere) {
        List<ProductVariant> dataResponse = new ArrayList<>();
        StringBuilder strSQL = new StringBuilder("SELECT ");
        strSQL.append("NVL(p.ID,0) as PRODUCT_ID_0, p.TEN_SAN_PHAM as PRODUCT_NAME_1, NVL(v.ID,0) as PRODUCT_VARIANT_ID_2, v.VARIANT_CODE as VARIANT_CODE_3, ");
        strSQL.append("v.VARIANT_NAME as PRODUCT_VARIANT_NAME_4, NVL(c.ID,0) as COLOR_ID_5, c.NAME as COLOR_NAME_6, NVL(s.ID,0) as SIZE_ID_7, s.NAME as SIZE_NAME_8, ");
        strSQL.append("NVL(f.ID,0) as FABRIC_ID_9, f.NAME as FABRIC_NAME_10, v.QUANTITY_STG as QUANTITY_STG_11, v.QUANTITY_SELL as QUANTITY_SELL_12, ");
        strSQL.append("NVL(g.ID,0) as GARMENT_FACTORY_ID_13, g.NAME as GARMENT_FACTORY_NAME_14, NVL(sp.ID,0) as SUPPLIER_ID_15, sp.NAME as SUPPLIER_NAME_16, ");
        strSQL.append("NVL(ti.ID,0) as TICKET_IMPORT_ID_17, ti.TITLE as TICKET_IMPORT_TITLE_18, NVL(pr.ID,0) as PRICE_ID_19, pr.GIA_BAN as PRICE_SELL_20, v.TRANG_THAI as PRODUCT_VARIANT_STATUS_21 ");
        strSQL.append("FROM pro_product_variant v ");
        strSQL.append("LEFT JOIN PRO_PRODUCT p ON p.ID = v.PRODUCT_ID ");
        strSQL.append("LEFT JOIN PRO_GARMENT_FACTORY g ON g.ID = v.GARMENT_FACTORY_ID ");
        strSQL.append("LEFT JOIN PRO_SUPPLIER sp ON sp.ID = v.SUPPLIER_ID ");
        strSQL.append("LEFT JOIN PRO_PRICE pr ON pr.PRODUCT_VARIANT_ID = v.ID AND pr.STATUS = '" + AppConstants.PRICE_STATUS.A.name() + "' ");
        strSQL.append("LEFT JOIN STG_TICKET_IMPORT_GOODS ti ON ti.ID = v.TICKET_IMPORT_ID ");
        strSQL.append("LEFT JOIN (SELECT * FROM CATEGORY WHERE TYPE = 'COLOR') c ON c.ID = v.COLOR_ID ");
        strSQL.append("LEFT JOIN (SELECT * FROM CATEGORY WHERE TYPE = 'SIZE') s ON s.ID = v.SIZE_ID ");
        strSQL.append("LEFT JOIN (SELECT * FROM CATEGORY WHERE TYPE = 'FABRICTYPE') f ON f.ID = v.FABRIC_ID ");
        if (AppConstants.PRODUCT.equals(where)) {
            strSQL.append("WHERE v.PRODUCT_ID = ?");
        }
        if (AppConstants.COLOR.equals(where)) {
            strSQL.append("WHERE v.COLOR_ID = ?");
        }
        if (AppConstants.SIZE.equals(where)) {
            strSQL.append("WHERE v.SIZE_ID = ?");
        }
        if (AppConstants.FABRICTYPE.equals(where)) {
            strSQL.append("WHERE v.FABRIC_ID = ?");
        }
        if (AppConstants.TICKETIMPORT.equals(where)) {
            strSQL.append("WHERE v.TICKET_IMPORT_ID = ?");
        }
        logger.info("[SQL findData]: " + strSQL.toString());
        Query query = entityManager.createNativeQuery(strSQL.toString());
        if (where != null) {
            query.setParameter(1, valueWhere);
        }
        List<Object[]> listData = query.getResultList();
        for (Object[] data : listData) {
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
            productVariant.setPrice(new Price(Integer.parseInt(String.valueOf(data[19])), Double.parseDouble(String.valueOf(data[20]))));
            productVariant.setTrangThai(String.valueOf(data[21]));
            dataResponse.add(productVariant);
        }
        return dataResponse;
    }
}