package com.flowiee.app.service.impl;

import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.Product;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.ProductHistory;
import com.flowiee.app.repository.CategoryRepository;
import com.flowiee.app.repository.ProductRepository;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.ErrorMessages;
import com.flowiee.app.utils.MessagesUtil;
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
    private static final String module = SystemModule.PRODUCT.name();

    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private FileStorageService fileService;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductHistoryService productHistoryService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private VoucherService voucherInfoService;
    @Autowired
    private VoucherApplyService voucherApplyService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Product> findAll() {
        Page<Product> products = productsRepository.findAll(null, null, null, Pageable.unpaged());
        return this.setImageActiveAndLoadVoucherApply(products);
    }

    @Override
    public Page<Product> findAll(Integer productTypeId, Integer brandId, String status) {
        Page<Product> products = productsRepository.findAll(productTypeId, brandId, status, Pageable.unpaged());
        return this.setImageActiveAndLoadVoucherApply(products);
    }

    @Override
    public Page<Product> findAll(int size, int page, Integer productTypeId, Integer brandId, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        Page<Product> products = productsRepository.findAll(productTypeId, brandId, status, pageable);
        return this.setImageActiveAndLoadVoucherApply(products);
    }

    @Override
    public List<Product> findProductIdAndProductName() {
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
    public Product findById(Integer id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Product product) {
        try {
            product.setCreatedBy(CommonUtil.getCurrentAccountId());
            product.setMoTaSanPham(product.getMoTaSanPham() != null ? product.getMoTaSanPham() : "");
            product.setStatus(AppConstants.PRODUCT_STATUS.INACTIVE.name());
            productsRepository.save(product);
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_CREATE.name(), "Thêm mới sản phẩm: " + product.toString());
            logger.info("Insert product success! " + product.toString());
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Insert product fail!", e);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Transactional
    @Override
    public String update(Product productToUpdate, Integer productId) {
    	Product productBefore = null;
        try {
            if (productToUpdate.getMoTaSanPham().isEmpty()) {
                productToUpdate.setMoTaSanPham("-");;
            }
            productBefore = this.findById(productId);
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
            productToUpdate.setLastUpdatedBy(CommonUtil.getCurrentAccountUsername());
            productsRepository.save(productToUpdate);
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
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_UPDATE.name(), "Cập nhật sản phẩm: " + noiDungLog, "Sản phẩm sau khi cập nhật: " + noiDungLogUpdate);
            logger.info("Update product success! productId=" + productId);
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Update product fail! productId=" + productId, e);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        try {
            Product productToDelete = this.findById(id);
            if (productInUse(id)) {
                throw new DataInUseException(ErrorMessages.ERROR_LOCKED);
            }
            productsRepository.deleteById(id);            
            systemLogService.writeLog(module, ProductAction.PRO_PRODUCT_DELETE.name(), "Xóa sản phẩm: " + productToDelete.toString());
            logger.info("Delete product success! productId=" + id);
            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            logger.error("Delete product fail! productId=" + id, e);
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
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
        String filePathOriginal = CommonUtil.PATH_TEMPLATE_EXCEL + "/" + AppConstants.TEMPLATE_E_SANPHAM + ".xlsx";
        String filePathTemp = CommonUtil.PATH_TEMPLATE_EXCEL + "/" + AppConstants.TEMPLATE_E_SANPHAM + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
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
                row.createCell(6).setCellValue(CommonUtil.formatToVND(giaBan));
                row.createCell(7).setCellValue(soLuong);
                row.createCell(8).setCellValue(daBan);

                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(CommonUtil.setBorder(workbook.createCellStyle()));
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

    @Override
    public List<Product> findByProductType(Integer productTypeId) {
        return productsRepository.findByProductType(productTypeId);
    }

    @Override
    public List<Product> findByUnit(Integer unitId) {
        return productsRepository.findByUnit(unitId);
    }

    @Override
    public List<Product> findByBrand(Integer brandId) {
        return productsRepository.findByBrand(brandId);
    }

    @Override
    public boolean productInUse(Integer productId) {
        return (!productVariantService.findAllProductVariantOfProduct(productId).isEmpty());
    }

    @Override
    public List<ProductDTO> setInfoVariant(List<ProductDTO> productDTOs) {
        for (ProductDTO p : productDTOs) {
            LinkedHashMap<String, String> variantInfo = new LinkedHashMap<>();
            int totalQtyStorage = 0;
            for (Category color : categoryRepository.findColorOfProduct(p.getProductId())) {
                StringBuilder sizeName = new StringBuilder();
                List<Category> listSize = categoryRepository.findSizeOfColorOfProduct(p.getProductId(), color.getId());
                for (int i = 0; i < listSize.size(); i++) {
                    int qtyStorage = productVariantService.findQuantityBySizeOfEachColor(p.getProductId(), color.getId(), listSize.get(i).getId());
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
            p.setTotalQtySell(productVariantService.findTotalQtySell(p.getProductId()));
        }
        return productDTOs;
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
}