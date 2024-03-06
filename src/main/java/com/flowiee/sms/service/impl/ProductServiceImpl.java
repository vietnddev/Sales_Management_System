package com.flowiee.sms.service.impl;

import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.model.ACTION;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.model.dto.ProductDTO;
import com.flowiee.sms.model.dto.ProductVariantDTO;
import com.flowiee.sms.entity.*;
import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.core.exception.DataInUseException;
import com.flowiee.sms.repository.CategoryRepository;
import com.flowiee.sms.repository.ProductAttributeRepository;
import com.flowiee.sms.repository.ProductDetailRepository;
import com.flowiee.sms.repository.ProductRepository;
import com.flowiee.sms.service.*;
import com.flowiee.sms.utils.AppConstants;
import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final String module = MODULE.PRODUCT.name();

    @Autowired private ProductRepository productsRepo;
    @Autowired private ProductDetailRepository productVariantRepo;
    @Autowired private ProductAttributeRepository productAttributeRepo;
    @Autowired private SystemLogService systemLogService;
    @Autowired private FileStorageService fileService;
    @Autowired private EntityManager entityManager;
    @Autowired private ProductHistoryService productHistoryService;
    @Autowired private VoucherService voucherInfoService;
    @Autowired private VoucherApplyService voucherApplyService;
    @Autowired private CategoryRepository categoryRepo;

    @Override
    public Page<ProductDTO> findAllProducts(int pageSize, int pageNum, String txtSearch, Integer productType, Integer color, Integer size) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        Page<Product> products = productsRepo.findAll(txtSearch, null, pageable);
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
    public List<Product> findProductsByType(Integer productTypeId) {
        return productsRepo.findByProductType(productTypeId);
    }

    @Override
    public List<ProductDetail> findAllProductVariants() {
        return this.extractProductVariantQuery(productVariantRepo.findAll(null, null, null, null, null));
    }

    @Override
    public List<ProductVariantDTO> findAllProductVariantOfProduct(Integer productId) {
        List<ProductVariantDTO> listReturn = new ArrayList<>();
        List<ProductDetail> data = this.extractProductVariantQuery(productVariantRepo.findAll(productId, null, null, null, null));
        for (ProductDetail productDetail : data) {
            listReturn.add(ProductVariantDTO.fromProductVariant(productDetail));
        }
        return listReturn;
    }

    @Override
    public List<ProductAttribute> findAllAttributes(Integer productVariantId) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productVariantId);
        return productAttributeRepo.findByProductVariantId(productDetail);
    }

    @Override
    public Product findProductById(Integer id) {
        return productsRepo.findById(id).orElse(null);
    }

    @Override
    public ProductDetail findProductVariantById(Integer productVariantId) {
        return productVariantRepo.findById(productVariantId).orElse(null);
    }

    @Override
    public ProductAttribute findProductAttributeById(Integer attributeId) {
        return productAttributeRepo.findById(attributeId).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        try {
            product.setCreatedBy(CommonUtils.getCurrentAccountId());
            product.setDescription(product.getDescription() != null ? product.getDescription() : "");
            product.setStatus(AppConstants.PRODUCT_STATUS.I.name());
            Product pSaved = productsRepo.save(product);
            systemLogService.writeLog(module, ACTION.PRO_PRODUCT_CREATE.name(), "Thêm mới sản phẩm: " + product);
            logger.info("Insert product success! " + product);
            return pSaved;
        } catch (Exception e) {
            logger.error("Insert product fail!", e);
            throw new AppException();
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Product productToUpdate, Integer productId) {
        Product productBefore = null;
        if (productToUpdate.getDescription().isEmpty()) {
            productToUpdate.setDescription("-");
        }
        productBefore = this.findProductById(productId);
        productBefore.compareTo(productToUpdate).forEach((key, value) -> {
            ProductHistory productHistory = new ProductHistory();
            productHistory.setTitle("Update product");
            productHistory.setProduct(new Product(productId));
            productHistory.setField(key);
            productHistory.setOldValue(value.substring(0, value.indexOf("#")));
            productHistory.setNewValue(value.substring(value.indexOf("#") + 1));
            productHistoryService.save(productHistory);
        });

        productToUpdate.setId(productId);
        productToUpdate.setLastUpdatedBy(CommonUtils.getCurrentAccountUsername());
        Product productUpdated = productsRepo.save(productToUpdate);
        String noiDungLog = "";
        String noiDungLogUpdate = "";
        if (productBefore.toString().length() > 1950) {
            noiDungLog = productBefore.toString().substring(0, 1950);
        } else {
            noiDungLog = productBefore.toString();
        }
        if (productToUpdate.toString().length() > 1950) {
            noiDungLogUpdate = productToUpdate.toString().substring(0, 1950);
        } else {
            noiDungLogUpdate = productToUpdate.toString();
        }
        systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật sản phẩm: " + noiDungLog, "Sản phẩm sau khi cập nhật: " + noiDungLogUpdate);
        logger.info("Update product success! productId=" + productId);
        return productUpdated;
    }

    @Transactional
    @Override
    public String deleteProduct(Integer id) {
        try {
            Product productToDelete = this.findProductById(id);
            if (productInUse(id)) {
                throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
            }
            productsRepo.deleteById(id);
            systemLogService.writeLog(module, ACTION.PRO_PRODUCT_DELETE.name(), "Xóa sản phẩm: " + productToDelete.toString());
            logger.info("Delete product success! productId=" + id);
            return MessageUtils.DELETE_SUCCESS;
        } catch (RuntimeException ex) {
            logger.error("Delete product fail! productId=" + id, ex);
            throw new AppException(ex);
        }
    }

    @Override
    public ProductDetail saveProductVariant(ProductVariantDTO productVariantDTO) {
        try {
            ProductDetail pVariant = ProductDetail.fromProductVariantDTO(productVariantDTO);
            pVariant.setStorageQty(0);
            pVariant.setSoldQty(0);
            pVariant.setStatus(AppConstants.PRODUCT_STATUS.A.name());
            pVariant.setVariantCode(CommonUtils.now("yyyyMMddHHmmss"));
            ProductDetail productDetailSaved = productVariantRepo.save(pVariant);
            systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Thêm mới biến thể sản phẩm: " + pVariant);
            logger.info("Insert productVariant success! " + pVariant);
            return productDetailSaved;
        } catch (Exception e) {
            logger.error(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product variant"), e);
            e.printStackTrace();
            throw new AppException();
        }
    }

    @Override
    public ProductDetail updateProductVariant(ProductDetail productDetail, Integer productVariantId) {
        ProductDetail productDetailBefore = this.findProductVariantById(productVariantId);
        try {
            productDetail.setId(productVariantId);
            ProductDetail productDetailUpdated = productVariantRepo.save(productDetail);
            systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật biến thể sản phẩm: " + productDetailBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + productDetail);
            logger.info("Update productVariant success! " + productDetail);
            return productDetailUpdated;
        } catch (Exception e) {
            logger.info("Update productVariant fail! " + productDetail.toString(), e);
            e.printStackTrace();
            throw new AppException();
        }
    }

    @Override
    public String deleteProductVariant(Integer productVariantId) {
        ProductDetail productDetailToDelete = this.findProductVariantById(productVariantId);
        try {
            productVariantRepo.deleteById(productVariantId);
            systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Xóa biến thể sản phẩm: " + productDetailToDelete.toString());
            logger.info("Delete productVariant success! " + productDetailToDelete);
            return MessageUtils.DELETE_SUCCESS;
        } catch (Exception ex) {
            logger.error("Delete productVariant fail! " + productDetailToDelete.toString(), ex);
            throw new AppException(ex);
        }
    }

    @Override
    public ProductAttribute saveProductAttribute(ProductAttribute productAttribute) {
        ProductAttribute productAttributeSaved = productAttributeRepo.save(productAttribute);
        systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Thêm mới thuộc tính sản phẩm");
        return productAttributeSaved;
    }

    @Override
    public ProductAttribute updateProductAttribute(ProductAttribute attribute, Integer attributeId) {
        attribute.setId(attributeId);
        ProductAttribute productAttributeUpdated = productAttributeRepo.save(attribute);
        systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật thuộc tính sản phẩm");
        return productAttributeUpdated;
    }

    @Override
    public String deleteProductAttribute(Integer attributeId) {
        productAttributeRepo.deleteById(attributeId);
        systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Xóa thuộc tính sản phẩm");
        return MessageUtils.DELETE_SUCCESS;
    }

    @Transactional
    @Override
    public String updateProductVariantQuantity(Integer quantity, Integer productVariantId, String type) {
        try {
            if ("I".equals(type)) {
                productVariantRepo.updateQuantityIncrease(quantity, productVariantId);
            } else if ("D".equals(type)) {
                productVariantRepo.updateQuantityDecrease(quantity, productVariantId);
            }
            systemLogService.writeLog(module, ACTION.PRO_PRODUCT_UPDATE.name(), "Update product quantity");
            return MessageUtils.UPDATE_SUCCESS;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!", e);
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product quantity"), e);
        }
    }

    @Transactional
    @Override
    public String updatePrice(Integer variantId, BigDecimal originalPrice, BigDecimal discountPrice) {
        try {
            ProductDetail productDetail = this.findProductVariantById(variantId);
            if (productDetail == null) {
                throw new BadRequestException();
            }
            if (originalPrice == null) {
                originalPrice = productDetail.getOriginalPrice();
            }
            if (discountPrice == null) {
                discountPrice = productDetail.getDiscountPrice();
            }
            productVariantRepo.updatePrice(originalPrice, discountPrice, variantId);
            //Log history change
            Integer productId = productDetail.getProduct().getId();
            String title = "Cập nhật giá bán - giá %s";
            String oldValue = String.valueOf(productDetail.getOriginalPrice());
            String newValue = String.valueOf(originalPrice);
            if (originalPrice != null) {
                productHistoryService.save(new ProductHistory(productId, variantId, null, String.format(title, "gốc"), "PRICE", oldValue, newValue));
            }
            if (originalPrice != null) {
                productHistoryService.save(new ProductHistory(productId, variantId, null, String.format(title, "giảm"), "PRICE", oldValue, newValue));
            }
            return MessageUtils.UPDATE_SUCCESS;
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "price"), ex);
            throw new AppException();
        }
    }

    @Override
    public Integer findProductVariantTotalQtySell(Integer productId) {
        return productVariantRepo.findTotalQtySell(productId);
    }

    @Override
    public Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId) {
        try {
            return productVariantRepo.findQuantityBySizeOfEachColor(productId, colorId, sizeId);
        } catch (RuntimeException ex) {
            logger.error("Error finding product variant quantity", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Integer totalProductsInStorage() {
        return productVariantRepo.countTotalQuantity();
    }

    @Override
    public boolean productInUse(Integer productId) throws RuntimeException {
        return (!this.findAllProductVariantOfProduct(productId).isEmpty());
    }

    @Override
    public boolean isProductVariantExists(int productId, int colorId, int sizeId) {
        ProductDetail productDetail = productVariantRepo.findByColorAndSize(productId, colorId, sizeId);
        return ObjectUtils.isNotEmpty(productDetail);
    }

    private List<ProductDetail> extractProductVariantQuery(List<Object[]> objects) throws RuntimeException {
        List<ProductDetail> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProduct(new Product(Integer.parseInt(String.valueOf(data[0])), String.valueOf(data[1])));
            productDetail.setId(Integer.parseInt(String.valueOf(data[2])));
            productDetail.setVariantCode(String.valueOf(data[3]));
            productDetail.setVariantName(String.valueOf(data[4]));
            productDetail.setColor(new Category(Integer.parseInt(String.valueOf(data[5])), String.valueOf(data[6])));
            productDetail.setSize(new Category(Integer.parseInt(String.valueOf(data[7])), String.valueOf(data[8])));
            productDetail.setFabricType(new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
            productDetail.setStorageQty(Integer.parseInt(String.valueOf(data[11])));
            productDetail.setSoldQty(Integer.parseInt(String.valueOf(data[12])));
            productDetail.setGarmentFactory(new GarmentFactory(Integer.parseInt(String.valueOf(data[13])), String.valueOf(data[14])));
            productDetail.setSupplier(new Supplier(Integer.parseInt(String.valueOf(data[15])), String.valueOf(data[16])));
            productDetail.setTicketImport(new TicketImport(Integer.parseInt(String.valueOf(data[17])), String.valueOf(data[18])));
            productDetail.setOriginalPrice(new BigDecimal(String.valueOf(data[19])));
            productDetail.setDiscountPrice(new BigDecimal(String.valueOf(data[20])));
            productDetail.setStatus(String.valueOf(data[21]));
            dataResponse.add(productDetail);
        }
        return dataResponse;
    }

    @Override
    public byte[] exportData(List<Integer> listSanPhamId) {
        StringBuilder strSQL = new StringBuilder("SELECT ");
        strSQL.append("lsp.TEN_LOAI as LOAI_SAN_PHAM, ").append("spbt.MA_SAN_PHAM, ").append("spbt.TEN_BIEN_THE, ").append("sz.TEN_LOAI as KICH_CO, ").append("cl.TEN_LOAI as MAU_SAC, ")
              .append("(SELECT spg.GIA_BAN FROM san_pham_gia spg WHERE spg.BIEN_THE_ID = spbt.ID AND spg.TRANG_THAI = 1) as GIA_BAN, ")
              .append("spbt.SO_LUONG_KHO, ").append("spbt.DA_BAN ");
        strSQL.append("FROM san_pham sp ");
        strSQL.append("LEFT JOIN san_pham_bien_the spbt ").append("on sp.ID = spbt.SAN_PHAM_ID ");
        strSQL.append("LEFT JOIN dm_loai_san_pham lsp ").append("on sp.LOAI_SAN_PHAM = lsp.ID ");
        strSQL.append("LEFT JOIN dm_loai_kich_co sz ").append(" on spbt.KICH_CO_ID = sz.ID ");
        strSQL.append("LEFT JOIN dm_loai_mau_sac cl on ").append(" spbt.MAU_SAC_ID = cl.ID ");
        strSQL.append("WHERE spbt.ID > 0 ");
        if (listSanPhamId != null) {
            strSQL.append("AND sp.ID IN (sp.ID)");
        } else {
            strSQL.append("AND  sp.ID IN (sp.ID)");
        }
        Query result = entityManager.createNativeQuery(strSQL.toString());
        @SuppressWarnings("unchecked")
        List<Object[]> listData = result.getResultList();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = CommonUtils.excelTemplatePath + "/" + AppConstants.TEMPLATE_E_SANPHAM + ".xlsx";
        String filePathTemp = CommonUtils.excelTemplatePath + "/" + AppConstants.TEMPLATE_E_SANPHAM + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                    Path.of(filePathTemp),
                    StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 3);

                String loaiSanPham = listData.get(i)[0] != null ? String.valueOf(listData.get(i)[0]) : "";
                String maSanPham = listData.get(i)[1] != null ? String.valueOf(listData.get(i)[1]) : "";
                String tenSanPham = listData.get(i)[2] != null ? String.valueOf(listData.get(i)[2]) : "";
                String kichCo = listData.get(i)[3] != null ? String.valueOf(listData.get(i)[3]) : "";
                String mauSac = listData.get(i)[4] != null ? String.valueOf(listData.get(i)[4]) : "";
                Double giaBan = listData.get(i)[5] != null ? Double.parseDouble(String.valueOf(listData.get(i)[5])) : 0;
                String soLuong = listData.get(i)[6] != null ? String.valueOf(listData.get(i)[6]) : "0";
                String daBan = listData.get(i)[7] != null ? String.valueOf(listData.get(i)[7]) : "";

                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(loaiSanPham);
                row.createCell(2).setCellValue(maSanPham);
                row.createCell(3).setCellValue(tenSanPham);
                row.createCell(4).setCellValue(kichCo);
                row.createCell(5).setCellValue(mauSac);
                row.createCell(6).setCellValue(CommonUtils.formatToVND(giaBan));
                row.createCell(7).setCellValue(soLuong);
                row.createCell(8).setCellValue(daBan);

                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(CommonUtils.setBorder(workbook.createCellStyle()));
                }
            }
            workbook.write(stream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileDeleteAfterExport.exists()) {
                fileDeleteAfterExport.delete();
            }
        }
        return stream.toByteArray();
    }

    private void setImageActiveAndLoadVoucherApply(List<ProductDTO> products) {
        if (products == null) {
            return;
        }
        for (ProductDTO p : products) {
            FileStorage imageActive = fileService.findImageActiveOfSanPham(p.getId());
            if (imageActive != null) {
                p.setImageActive("/" + imageActive.getDirectoryPath() + "/" + imageActive.getStorageName());
            }
            List<Integer> listVoucherInfoId = new ArrayList<>();
            voucherApplyService.findByProductId(p.getId()).forEach(voucherApplyDTO -> {
                listVoucherInfoId.add(voucherApplyDTO.getVoucherInfoId());
            });
            if (!listVoucherInfoId.isEmpty()) {
                p.setListVoucherInfoApply(voucherInfoService.findAllVouchers(listVoucherInfoId, AppConstants.VOUCHER_STATUS.A.name()));
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
                        int qtyStorage = this.findProductVariantQuantityBySizeOfEachColor(p.getId(), color.getId(), listSize.get(i).getId());
                        if (i == listSize.size() - 1) {
                            sizeName.append(listSize.get(i).getName()).append(" (").append(qtyStorage).append(")");
                        } else {
                            sizeName.append(listSize.get(i).getName()).append(" (").append(qtyStorage).append(")").append(", ");
                        }
                        totalQtyStorage += qtyStorage;
                    }
                    variantInfo.put(color.getName(), sizeName.toString());
                }
                p.setProductVariantInfo(variantInfo);

                p.setTotalQtyStorage(totalQtyStorage);
                p.setTotalQtySell(this.findProductVariantTotalQtySell(p.getId()));
            }
        }
    }
}