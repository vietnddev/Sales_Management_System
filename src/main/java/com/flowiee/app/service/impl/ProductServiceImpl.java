package com.flowiee.app.service.impl;

import com.flowiee.app.dto.PriceDTO;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.repository.CategoryRepository;
import com.flowiee.app.repository.ProductAttributeRepository;
import com.flowiee.app.repository.ProductRepository;
import com.flowiee.app.repository.ProductVariantRepository;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final String module = AppConstants.SYSTEM_MODULE.PRODUCT.name();

    @Autowired
    private ProductRepository          productsRepository;
    @Autowired
    private ProductVariantRepository   productVariantRepository;
    @Autowired
    private ProductAttributeRepository productAttributeRepository;
    @Autowired
    private PriceService               priceService;
    @Autowired
    private SystemLogService           systemLogService;
    @Autowired
    private FileStorageService         fileService;
    @Autowired
    private EntityManager              entityManager;
    @Autowired
    private ProductHistoryService      productHistoryService;
    @Autowired
    private VoucherService             voucherInfoService;
    @Autowired
    private VoucherApplyService        voucherApplyService;
    @Autowired
    private CategoryRepository         categoryRepository;

    @Override
    public Page<Product> findAllProducts() {
        Page<Product> products = productsRepository.findAll(null, null, null, Pageable.unpaged());
        return this.setImageActiveAndLoadVoucherApply(products);
    }

    @Override
    public Page<Product> findAllProducts(Integer productTypeId, Integer brandId, String status) {
        Page<Product> products = productsRepository.findAll(productTypeId, brandId, status, Pageable.unpaged());
        return this.setImageActiveAndLoadVoucherApply(products);
    }

    @Override
    public Page<Product> findAllProducts(int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> products = productsRepository.findAll(null, null, null, pageable);
        return this.setImageActiveAndLoadVoucherApply(products);
    }

    @Override
    public List<Product> findProductsIdAndProductName() {
        List<Product> products = new ArrayList<>();
        productsRepository.findIdAndName(AppConstants.PRODUCT_STATUS.ACTIVE.name()).forEach(objects -> {
            Product p = new Product();
            p.setId(Integer.parseInt(String.valueOf(objects[0])));
            p.setTenSanPham(String.valueOf(objects[1]));
            products.add(p);
        });
        return products;
    }

    @Override
    public List<Product> findProductsByType(Integer productTypeId) {
        return productsRepository.findByProductType(productTypeId);
    }

    @Override
    public List<Product> findProductsByUnit(Integer unitId) {
        return productsRepository.findByUnit(unitId);
    }

    @Override
    public List<Product> findProductsByBrand(Integer brandId) {
        return productsRepository.findByBrand(brandId);
    }

    @Override
    public List<ProductDTO> setInfoVariantOfProduct(List<ProductDTO> productDTOs) {
        for (ProductDTO p : productDTOs) {
            LinkedHashMap<String, String> variantInfo = new LinkedHashMap<>();
            int totalQtyStorage = 0;
            for (Category color : categoryRepository.findColorOfProduct(p.getProductId())) {
                StringBuilder sizeName = new StringBuilder();
                List<Category> listSize = categoryRepository.findSizeOfColorOfProduct(p.getProductId(), color.getId());
                for (int i = 0; i < listSize.size(); i++) {
                    int qtyStorage = this.findProductVariantQuantityBySizeOfEachColor(p.getProductId(), color.getId(), listSize.get(i).getId());
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
            p.setTotalQtySell(this.findProductVariantTotalQtySell(p.getProductId()));
        }
        return productDTOs;
    }

    @Override
    public List<ProductVariant> findAllProductVariants() {
        return this.extractProductVariantQuery(productVariantRepository.findAll(null, null, null, null, null));
    }

    @Override
    public List<ProductVariant> findProductVariantBySize(Integer sizeId) {
        return this.extractProductVariantQuery(productVariantRepository.findAll(null, null, null, sizeId, null));
    }

    @Override
    public List<ProductVariant> findProductVariantByColor(Integer colorId) {
        return this.extractProductVariantQuery(productVariantRepository.findAll(null, null, colorId, null, null));
    }

    @Override
    public List<ProductVariant> findProductVariantByImport(Integer ticketImportId) {
        return this.extractProductVariantQuery(productVariantRepository.findAll(null, ticketImportId, null, null, null));
    }

    @Override
    public List<ProductVariant> findProductVariantByFabricType(Integer fabricTypeId) {
        return this.extractProductVariantQuery(productVariantRepository.findAll(null, null, null, null, fabricTypeId));
    }

    @Override
    public List<ProductVariantDTO> findAllProductVariantOfProduct(Integer productId) {
        List<ProductVariantDTO> listReturn = new ArrayList<>();
        List<ProductVariant> data = this.extractProductVariantQuery(productVariantRepository.findAll(productId, null, null, null, null));
        for (ProductVariant productVariant : data) {
            ProductVariantDTO dataModel = ProductVariantDTO.fromProductVariant(productVariant);
            List<PriceDTO> listPriceOfProductVariant = priceService.findPricesByProductVariant(dataModel.getProductVariantId());
            PriceDTO price = PriceDTO.fromPrice(priceService.findGiaHienTai(dataModel.getProductVariantId()));
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
    public List<ProductAttribute> findAllAttributes(Integer productVariantId) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantId);
        return productAttributeRepository.findByProductVariantId(productVariant);
    }

    @Override
    public Product findProductById(Integer id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public ProductVariant findProductVariantById(Integer productVariantId) {
        return productVariantRepository.findById(productVariantId).orElse(null);
    }

    @Override
    public ProductAttribute findProductAttributeById(Integer attributeId) {
        return productAttributeRepository.findById(attributeId).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        try {
            product.setCreatedBy(CommonUtils.getCurrentAccountId());
            product.setMoTaSanPham(product.getMoTaSanPham() != null ? product.getMoTaSanPham() : "");
            product.setStatus(AppConstants.PRODUCT_STATUS.INACTIVE.name());
            Product pSaved = productsRepository.save(product);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_CREATE.name(), "Thêm mới sản phẩm: " + product.toString());
            logger.info("Insert product success! " + product.toString());
            return pSaved;
        } catch (Exception e) {
            logger.error("Insert product fail!", e);
            throw new ApiException();
        }
    }

    @Transactional
    @Override
    public Product updateProduct(Product productToUpdate, Integer productId) {
        Product productBefore = null;
        if (productToUpdate.getMoTaSanPham().isEmpty()) {
            productToUpdate.setMoTaSanPham("-");
        }
        productBefore = this.findProductById(productId);
        productBefore.compareTo(productToUpdate).forEach((key, value) -> {
            ProductHistory productHistory = new ProductHistory();
            productHistory.setTitle("Update product");
            productHistory.setProduct(new Product(productId));
            productHistory.setFieldName(key);
            productHistory.setOldValue(value.substring(0, value.indexOf("#")));
            productHistory.setNewValue(value.substring(value.indexOf("#") + 1));
            productHistoryService.save(productHistory);
        });

        productToUpdate.setId(productId);
        productToUpdate.setLastUpdatedBy(CommonUtils.getCurrentAccountUsername());
        Product productUpdated = productsRepository.save(productToUpdate);
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
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật sản phẩm: " + noiDungLog, "Sản phẩm sau khi cập nhật: " + noiDungLogUpdate);
        logger.info("Update product success! productId=" + productId);
        return productUpdated;
    }

    @Transactional
    @Override
    public String deleteProduct(Integer id) {
        try {
            Product productToDelete = this.findProductById(id);
            if (productInUse(id)) {
                throw new DataInUseException(MessageUtils.ERROR_LOCKED);
            }
            productsRepository.deleteById(id);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_DELETE.name(), "Xóa sản phẩm: " + productToDelete.toString());
            logger.info("Delete product success! productId=" + id);
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Delete product fail! productId=" + id, e);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public ProductVariant saveProductVariant(ProductVariant productVariant) {
        try {
            String tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
//            if (productVariant.getTenBienThe().isEmpty()) {
//                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
//            } else {
//                tenBienTheSanPham = productVariant.getProduct().getTenSanPham() + " - " + productVariant.getTenBienThe() + " - Size " + productVariant.getSize().getName() + " - Màu " + productVariant.getColor().getName();
//            }
            productVariant.setTenBienThe(tenBienTheSanPham);
            ProductVariant productVariantSaved = productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Thêm mới biến thể sản phẩm: " + productVariant.toString());
            logger.info("Insert productVariant success! " + productVariant.toString());
            return productVariantSaved;
        } catch (Exception e) {
            logger.error("Insert productVariant fail! " + productVariant.toString(), e);
            e.printStackTrace();
            throw new ApiException();
        }
    }

    @Override
    public ProductVariant updateProductVariant(ProductVariant productVariant, Integer productVariantId) {
        ProductVariant productVariantBefore = this.findProductVariantById(productVariantId);
        try {
            productVariant.setId(productVariantId);
            ProductVariant productVariantUpdated = productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật biến thể sản phẩm: " + productVariantBefore.toString(), "Biến thể sản phẩm sau khi cập nhật: " + productVariant);
            logger.info("Update productVariant success! " + productVariant.toString());
            return productVariantUpdated;
        } catch (Exception e) {
            logger.info("Update productVariant fail! " + productVariant.toString(), e);
            e.printStackTrace();
            throw new ApiException();
        }
    }

    @Override
    public String deleteProductVariant(Integer productVariantId) {
        ProductVariant productVariantToDelete = this.findProductVariantById(productVariantId);
        try {
            productVariantRepository.deleteById(productVariantId);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Xóa biến thể sản phẩm: " + productVariantToDelete.toString());
            logger.info("Delete productVariant success! " + productVariantToDelete.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Delete productVariant fail! " + productVariantToDelete.toString(), e);
            e.printStackTrace();
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public ProductAttribute saveProductAttribute(ProductAttribute productAttribute) {
        ProductAttribute productAttributeSaved = productAttributeRepository.save(productAttribute);
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Thêm mới thuộc tính sản phẩm");
        return productAttributeSaved;
    }

    @Override
    public ProductAttribute updateProductAttribute(ProductAttribute attribute, Integer attributeId) {
        attribute.setId(attributeId);
        ProductAttribute productAttributeUpdated = productAttributeRepository.save(attribute);
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật thuộc tính sản phẩm");
        return productAttributeUpdated;
    }

    @Override
    public String deleteProductAttribute(Integer attributeId) {
        productAttributeRepository.deleteById(attributeId);
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Xóa thuộc tính sản phẩm");
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String updateProductVariantQuantity(Integer quantity, Integer id) {
        ProductVariant productVariant = this.findProductVariantById(id);
        productVariant.setSoLuongKho(productVariant.getSoLuongKho() - quantity);
        try {
            productVariantRepository.save(productVariant);
            systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_PRODUCT_UPDATE.name(), "Cập nhật lại số lượng sản phẩm khi tạo đơn hàng");
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật số lượng sản phẩm!");
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public Integer findProductVariantTotalQtySell(Integer productId) {
        return productVariantRepository.findTotalQtySell(productId);
    }

    @Override
    public Integer findProductVariantQuantityBySizeOfEachColor(Integer productId, Integer colorId, Integer sizeId) {
        return productVariantRepository.findQuantityBySizeOfEachColor(productId, colorId, sizeId);
    }

    @Override
    public Double findProductVariantPriceSell(int id) {
        if (priceService.findGiaHienTai(id) != null) {
            return priceService.findGiaHienTai(id).getGiaBan();
        }
        return null;
    }

    @Override
    public boolean productInUse(Integer productId) {
        return (!this.findAllProductVariantOfProduct(productId).isEmpty());
    }

    private Page<Product> setImageActiveAndLoadVoucherApply(Page<Product> products) {
        for (Product p : products) {
            FileStorage imageActive = fileService.findImageActiveOfSanPham(p.getId());
            p.setImageActive(Objects.requireNonNullElseGet(imageActive, FileStorage::new));

            List<Integer> listVoucherInfoId = new ArrayList<>();
            voucherApplyService.findByProductId(p.getId()).forEach(voucherApplyDTO -> {
                listVoucherInfoId.add(voucherApplyDTO.getVoucherInfoId());
            });
            if (!listVoucherInfoId.isEmpty()) {
                p.setListVoucherInfoApply(voucherInfoService.findByIds(listVoucherInfoId, AppConstants.VOUCHER_STATUS.ACTIVE.name()));
            }
        }
        return products;
    }

    private List<ProductVariant> extractProductVariantQuery(List<Object[]> objects) {
        List<ProductVariant> dataResponse = new ArrayList<>();
        for (Object[] data : objects) {
            ProductVariant productVariant = new ProductVariant();
            productVariant.setProduct(new Product(Integer.parseInt(String.valueOf(data[0])), String.valueOf(data[1])));
            productVariant.setId(Integer.parseInt(String.valueOf(data[2])));
            productVariant.setMaSanPham(String.valueOf(data[3]));
            productVariant.setTenBienThe(String.valueOf(data[4]));
            productVariant.setColor(new Category(Integer.parseInt(String.valueOf(data[5])), String.valueOf(data[6])));
            productVariant.setSize(new Category(Integer.parseInt(String.valueOf(data[7])), String.valueOf(data[8])));
            productVariant.setFabricType(new Category(Integer.parseInt(String.valueOf(data[9])), String.valueOf(data[10])));
            productVariant.setSoLuongKho(Integer.parseInt(String.valueOf(data[11])));
            productVariant.setSoLuongDaBan(Integer.parseInt(String.valueOf(data[12])));
            productVariant.setGarmentFactory(new GarmentFactory(Integer.parseInt(String.valueOf(data[13])), String.valueOf(data[14])));
            productVariant.setSupplier(new Supplier(Integer.parseInt(String.valueOf(data[15])), String.valueOf(data[16])));
            productVariant.setTicketImport(new TicketImport(Integer.parseInt(String.valueOf(data[17])), String.valueOf(data[18])));
            Integer priceId = data[19] != null ? Integer.parseInt(String.valueOf(data[19])) : null;
            Double priceSellValue = data[20] != null ? Double.parseDouble(String.valueOf(data[20])) : null;
            productVariant.setPrice(new Price(priceId, priceSellValue));
            productVariant.setTrangThai(String.valueOf(data[21]));
            dataResponse.add(productVariant);
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
        String filePathOriginal = CommonUtils.PATH_TEMPLATE_EXCEL + "/" + AppConstants.TEMPLATE_E_SANPHAM + ".xlsx";
        String filePathTemp = CommonUtils.PATH_TEMPLATE_EXCEL + "/" + AppConstants.TEMPLATE_E_SANPHAM + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
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
}