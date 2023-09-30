package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.ExcelUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.repository.LoaiTaiLieuRepository;
import com.flowiee.app.danhmuc.service.LoaiTaiLieuService;
import com.flowiee.app.hethong.model.SystemLogAction;
import com.flowiee.app.hethong.model.action.DanhMucAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.SystemLogService;
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
public class LoaiTaiLieuServiceImpl implements LoaiTaiLieuService {
    @Autowired
    private LoaiTaiLieuRepository loaiTaiLieuRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<LoaiTaiLieu> findAll() {
        return loaiTaiLieuRepository.findAll();
    }

    @Override
    public List<LoaiTaiLieu> findAllWhereStatusTrue() {
        return loaiTaiLieuRepository.findByTrangThai(true);
    }

    @Override
    public LoaiTaiLieu findById(Integer id) {
        return loaiTaiLieuRepository.findById(id).orElse(null);
    }

    @Override
    public LoaiTaiLieu findByTen(String ten) {
        return loaiTaiLieuRepository.findByTen(ten);
    }

    @Override
    public LoaiTaiLieu findDocTypeDefault() {
        return loaiTaiLieuRepository.findDocTypeDefault(true);
    }

    @Override
    public LoaiTaiLieu save(LoaiTaiLieu loaiTaiLieu) {
        if (findByTen(loaiTaiLieu.getTen()) != null) {
            return null;
        }
        if (loaiTaiLieu.isDefault() == true) {
            LoaiTaiLieu docTypeDefault = this.findDocTypeDefault();
            docTypeDefault.setDefault(false);
            loaiTaiLieuRepository.save(docTypeDefault);
        }
        LoaiTaiLieu loaiTaiLieuSaved = loaiTaiLieuRepository.save(loaiTaiLieu);
        systemLogService.writeLog(SystemModule.KHO_TAI_LIEU.name(), SystemLogAction.THEM_MOI.name(), loaiTaiLieu.toString(), null);
        return loaiTaiLieuSaved;
    }

    @Override
    public void update(LoaiTaiLieu loaiTaiLieu, Integer id) {
        if (id != null && this.findById(id) != null) {
            if (loaiTaiLieu.isDefault() == true) {
                LoaiTaiLieu docTypeDefault = this.findDocTypeDefault();
                docTypeDefault.setDefault(false);
                loaiTaiLieuRepository.save(docTypeDefault);
            }
            loaiTaiLieu.setId(id);
            LoaiTaiLieu loaiTaiLieuUpdated = loaiTaiLieuRepository.save(loaiTaiLieu);
            systemLogService.writeLog(SystemModule.KHO_TAI_LIEU.name(), DanhMucAction.UPDATE_DANHMUC.name(), loaiTaiLieu.toString(), loaiTaiLieuUpdated.toString());
        }
    }

    @Override
    public boolean delete(Integer id) {
        LoaiTaiLieu loaiTaiLieuToDelete = findById(id);
        if (loaiTaiLieuToDelete == null) {
            throw new BadRequestException();
        }
        loaiTaiLieuRepository.deleteById(id);
        systemLogService.writeLog(SystemModule.KHO_TAI_LIEU.name(), DanhMucAction.UPDATE_DANHMUC.name(), loaiTaiLieuToDelete.toString(), null);
        if (findById(id) == null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String importData(MultipartFile fileImport) {
        return null;
    }

    @Override
    public byte[] exportData() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_DM_LOAITAILIEU + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_DM_LOAITAILIEU + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                    Path.of(filePathTemp),
                    StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<LoaiTaiLieu> listData = this.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getMaLoai());
                row.createCell(2).setCellValue(listData.get(i).getTen());
                row.createCell(3).setCellValue(listData.get(i).getMoTa());
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