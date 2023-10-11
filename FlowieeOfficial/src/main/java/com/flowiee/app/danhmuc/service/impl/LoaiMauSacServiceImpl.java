package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.ExcelUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import com.flowiee.app.danhmuc.repository.LoaiMauSacRepository;
import com.flowiee.app.danhmuc.service.LoaiMauSacService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class LoaiMauSacServiceImpl implements LoaiMauSacService {
    @Autowired
    private LoaiMauSacRepository loaiMauSacRepository;

    @Override
    public List<LoaiMauSac> findAll() {
        return loaiMauSacRepository.findAll();
    }

    @Override
    public LoaiMauSac findById(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return loaiMauSacRepository.findById(id).orElse(null);
    }

    @Override
    public String save(LoaiMauSac loaiMauSac) {
        try {
            loaiMauSacRepository.save(loaiMauSac);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(LoaiMauSac loaiMauSac, int id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            loaiMauSacRepository.save(loaiMauSac);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String delete(int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        loaiMauSacRepository.deleteById(id);
        if (this.findById(id) == null) {
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String importData(MultipartFile fileImport) {
        return null;
    }

    @Override
    public byte[] exportTemplate() {
        return FileUtil.exportTemplate(FileUtil.TEMPLATE_DM_LOAIMAUSAC);
    }

    @Override
    public byte[] exportData() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_DM_LOAIMAUSAC + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_DM_LOAIMAUSAC + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                    Path.of(filePathTemp),
                    StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<LoaiMauSac> listData = this.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getMaLoai());
                row.createCell(2).setCellValue(listData.get(i).getTenLoai());
                row.createCell(3).setCellValue(listData.get(i).getGhiChu());
                for (int j = 0; j <= 3; j++) {
                    row.getCell(j).setCellStyle(ExcelUtil.setBorder(workbook.createCellStyle()));
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