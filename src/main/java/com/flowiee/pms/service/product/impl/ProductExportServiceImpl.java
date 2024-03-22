package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.service.product.ProductExportService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProductExportServiceImpl implements ProductExportService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public byte[] exportToExcel(Integer pProductId, List<Integer> pProductIds, boolean isExportAll) {
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
        if (pProductIds != null) {
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

    @Override
    public byte[] exportToCSV(Integer pProductId, List<Integer> pProductIds, boolean isExportAll) {
        return new byte[0];
    }

    @Override
    public byte[] exportToPDF(Integer pProductId, List<Integer> pProductIds, boolean isExportAll) {
        return new byte[0];
    }
}