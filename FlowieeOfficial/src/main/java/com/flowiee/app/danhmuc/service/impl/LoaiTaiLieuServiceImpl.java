package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.ExcelUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.repository.LoaiTaiLieuRepository;
import com.flowiee.app.danhmuc.service.LoaiTaiLieuService;
import com.flowiee.app.common.action.KhoTaiLieuAction;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.khotailieu.service.impl.DocumentServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LoaiTaiLieuServiceImpl.class);
    private static final String module = SystemModule.KHO_TAI_LIEU.name();

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
    public String save(LoaiTaiLieu loaiTaiLieu) {
        if (findByTen(loaiTaiLieu.getTen()) != null) {
            return null;
        }
        if (loaiTaiLieu.isDefault() == true) {
            LoaiTaiLieu docTypeDefault = this.findDocTypeDefault();
            docTypeDefault.setDefault(false);
            loaiTaiLieuRepository.save(docTypeDefault);
        }
        LoaiTaiLieu loaiTaiLieuSaved = loaiTaiLieuRepository.save(loaiTaiLieu);
        systemLogService.writeLog(module, KhoTaiLieuAction.DOCTYPE_CONFIG_DOCUMENT.name(), "Thêm mới loại tài liệu: " + loaiTaiLieuSaved.toString());
        logger.info(DocumentServiceImpl.class.getName() + ": Thêm mới loại tài liệu " + loaiTaiLieuSaved.toString());
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(LoaiTaiLieu loaiTaiLieu, Integer id) {
        if (id != null && this.findById(id) != null) {
            if (loaiTaiLieu.isDefault() == true) {
                LoaiTaiLieu docTypeDefault = this.findDocTypeDefault();
                docTypeDefault.setDefault(false);
                loaiTaiLieuRepository.save(docTypeDefault);
            }
            loaiTaiLieu.setId(id);
            LoaiTaiLieu loaiTaiLieuUpdated = loaiTaiLieuRepository.save(loaiTaiLieu);
            systemLogService.writeLog(module, KhoTaiLieuAction.DOCTYPE_CONFIG_DOCUMENT.name(), "Cập nhật loại tài liệu: " + loaiTaiLieuUpdated.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Cập nhật loại tài liệu " + loaiTaiLieuUpdated.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        }
        return TagName.SERVICE_RESPONSE_FAIL;
    }

    @Override
    public String delete(Integer id) {
        LoaiTaiLieu loaiTaiLieuToDelete = findById(id);
        if (loaiTaiLieuToDelete == null) {
            throw new BadRequestException();
        }
        loaiTaiLieuRepository.deleteById(id);
        if (findById(id) == null) {
            systemLogService.writeLog(module, KhoTaiLieuAction.DOCTYPE_CONFIG_DOCUMENT.name(), "Xóa loại tài liệu: " + loaiTaiLieuToDelete.toString());
            logger.info(DocumentServiceImpl.class.getName() + ": Xóa loại tài liệu " + loaiTaiLieuToDelete.toString());
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } else {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String importData(MultipartFile fileImport) {
        return null;
    }

    @Override
    public byte[] exportTemplate() {
        return FileUtil.exportTemplate(FileUtil.TEMPLATE_IE_DM_LOAITAILIEU);
    }

    @Override
    public byte[] exportData() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_IE_DM_LOAITAILIEU + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_IE_DM_LOAITAILIEU + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
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
        logger.info(LoaiTaiLieuServiceImpl.class.getName() + ": Export loại tài liệu");
        return stream.toByteArray();
    }
}