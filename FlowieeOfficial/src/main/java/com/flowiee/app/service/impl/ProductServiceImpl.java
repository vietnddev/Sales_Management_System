package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.*;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.Product;
import com.flowiee.app.common.action.SanPhamAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.repository.product.ProductRepository;
import com.flowiee.app.service.product.ProductService;
import com.flowiee.app.service.storage.FileStorageService;
import com.flowiee.app.service.system.SystemLogService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private FileStorageService fileService;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        List<Product> listProduct = productsRepository.findAll();
        for (int i = 0; i < listProduct.size(); i++) {
            FileStorage imageActive = fileService.findImageActiveOfSanPham(listProduct.get(i).getId());
            if (imageActive != null) {
                listProduct.get(i).setImageActive(imageActive);
            } else {
                listProduct.get(i).setImageActive(new FileStorage());
            }
        }
        return listProduct;
    }

    @Override
    public Product findById(Integer id) {
        Product product = new Product();
        if (id > 0) {
            product = productsRepository.findById(id).orElse(null);
        }
        if (product != null) {
            return product;
        } else {
            logger.error("Lỗi khi findById sản phẩm!");
            return new Product();
        }
    }

    @Override
    public String save(Product product) {
        try {
            product.setCreatedBy(FlowieeUtil.ACCOUNT_ID);
            productsRepository.save(product);
            systemLogService.writeLog(module, SanPhamAction.CREATE_SANPHAM.name(), "Thêm mới sản phẩm: " + product.toString());
            logger.info(ProductServiceImpl.class.getName() + ": Thêm mới sản phẩm " + product.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ProductServiceImpl.class.getName() + ": Lỗi khi thêm mới sản phẩm", e.getCause());
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Transactional
    @Override
    public String update(Product product, Integer id) {
        Product productBefore = this.findById(id);
        try {
            product.setId(id);
            product.setLastUpdatedBy(FlowieeUtil.ACCOUNT_ID + "");
            productsRepository.save(product);
            String noiDungLog = "";
            String noiDungLogUpdate = "";
            if (productBefore.toString().length() > 1950) {
                noiDungLog = productBefore.toString().substring(0, 1950);
            } else {
                noiDungLog = productBefore.toString();
            }
            if (product.toString().length() > 1950) {
                noiDungLogUpdate = product.toString().substring(0, 1950);
            } else {
                noiDungLogUpdate = product.toString();
            }
            systemLogService.writeLog(module, SanPhamAction.UPDATE_SANPHAM.name(), "Cập nhật sản phẩm: " + noiDungLog, "Sản phẩm sau khi cập nhật: " + noiDungLogUpdate);
            logger.info(ProductServiceImpl.class.getName() + ": Cập nhật sản phẩm " + productBefore.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ProductServiceImpl.class.getName() + ": Lỗi khi cập nhật sản phẩm!", e.getCause());
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        Product productToDelete = this.findById(id);
        try {
            productsRepository.deleteById(id);
            logger.info(ProductServiceImpl.class.getName() + ": Xóa sản phẩm " + productToDelete.toString());
            systemLogService.writeLog(module, SanPhamAction.DELETE_SANPHAM.name(), "Xóa sản phẩm: " + productToDelete.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ProductServiceImpl.class.getName() + ": Lỗi khi xóa sản phẩm", e.getCause());
            return TagName.SERVICE_RESPONSE_FAIL;
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
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_E_SANPHAM + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_E_SANPHAM + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
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
                row.createCell(6).setCellValue(FlowieeUtil.formatToVND(giaBan));
                row.createCell(7).setCellValue(soLuong);
                row.createCell(8).setCellValue(daBan);

                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(FileUtil.setBorder(workbook.createCellStyle()));
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
}