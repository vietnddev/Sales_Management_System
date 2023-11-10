package com.flowiee.app.category.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.common.utils.*;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.FlowieeImport;
import com.flowiee.app.entity.Notification;
import com.flowiee.app.repository.storage.FileStorageRepository;
import com.flowiee.app.repository.system.FlowieeImportRepository;
import com.flowiee.app.service.storage.FileStorageService;
import com.flowiee.app.service.system.FlowieeImportService;
import com.flowiee.app.service.system.NotificationService;
import com.flowiee.app.category.entity.DonViTinh;
import com.flowiee.app.category.repository.DonViTinhRepository;
import com.flowiee.app.category.service.DonViTinhService;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class DonViTinhServiceImpl implements DonViTinhService {
    private static final String MODULE = SystemModule.DANH_MUC.name();

    @Autowired
    private DonViTinhRepository donViTinhRepository;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private FlowieeImportService flowieeImportService;
    @Autowired
    private FlowieeImportRepository flowieeImportRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Override
    public List<DonViTinh> findAll() {
        return donViTinhRepository.findAll();
    }

    @Override
    public DonViTinh findById(Integer id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        return donViTinhRepository.findById(id).orElse(null);
    }

    @Override
    public String save(DonViTinh donViTinh) {
        if (donViTinh == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        try {
            donViTinhRepository.save(donViTinh);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String update(DonViTinh donViTinh, Integer id) {
        try {
            if (id <= 0 || this.findById(id) == null) {
                throw new NotFoundException();
            }
            donViTinh.setId(id);
            donViTinhRepository.save(donViTinh);
            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return TagName.SERVICE_RESPONSE_FAIL;
        }
    }

    @Override
    public String delete(Integer id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        donViTinhRepository.deleteById(id);
        if (this.findById(id) == null) {
            return TagName.SERVICE_RESPONSE_SUCCESS;
        }
        return TagName.SERVICE_RESPONSE_FAIL;
    }

    @Transactional
    @Override
    public String importData(MultipartFile pFileImport) {
        Date startTimeImport = new Date();
        String resultOfFlowieeImport = "";
        String detailOfFlowieeImport = "";
        int importSuccess = 0;
        int totalRecord = 0;
        boolean isImportSuccess = true;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(pFileImport.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    String categoryCode = row.getCell(1).getStringCellValue();
                    String categoryName = row.getCell(2).getStringCellValue();
                    String categoryNote = row.getCell(3).getStringCellValue();
                    //Nếu name null -> không ínsert data null vào database
                    if (categoryName == null || categoryName.length() == 0) {
                        XSSFCellStyle cellStyle = workbook.createCellStyle();
                        XSSFFont fontStyle = workbook.createFont();
                        row.getCell(1).setCellStyle(ExcelUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(2).setCellStyle(ExcelUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(3).setCellStyle(ExcelUtil.highlightDataImportEror(cellStyle, fontStyle));
                        continue;
                    }

                    DonViTinh donViTinh = new DonViTinh();
                    donViTinh.setMaLoai(!categoryCode.isEmpty() ? categoryCode : FlowieeUtil.getMaDanhMuc(categoryName));
                    donViTinh.setTenLoai(categoryName);
                    donViTinh.setGhiChu(categoryNote);

                    if (!"OK".equals(this.save(donViTinh))) {
                        isImportSuccess = false;
                        XSSFCellStyle cellStyle = workbook.createCellStyle();
                        XSSFFont fontStyle = workbook.createFont();
                        row.getCell(1).setCellStyle(ExcelUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(2).setCellStyle(ExcelUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(3).setCellStyle(ExcelUtil.highlightDataImportEror(cellStyle, fontStyle));
                    } else {
                        importSuccess++;
                    }
                    totalRecord++;
                }
            }
            workbook.close();

            if (isImportSuccess) {
                resultOfFlowieeImport = NotificationUtil.IMPORT_DM_DONVITINH_SUCCESS;
                detailOfFlowieeImport = importSuccess + " / " + totalRecord;
            } else {
                resultOfFlowieeImport = NotificationUtil.IMPORT_DM_DONVITINH_FAIL;
                detailOfFlowieeImport = importSuccess + " / " + totalRecord;
            }
            //Save file attach to storage
            FileStorage fileStorage = new FileStorage(pFileImport, SystemModule.DANH_MUC.name());
            fileStorage.setGhiChu("IMPORT");
            fileStorage.setStatus(false);
            fileStorage.setActive(false);
            fileStorageService.saveFileOfImport(pFileImport, fileStorage);

            //Save import
            FlowieeImport flowieeImport = new FlowieeImport();
            flowieeImport.setModule(MODULE);
            flowieeImport.setEntity(DonViTinhServiceImpl.class.getName());
            flowieeImport.setAccount(FlowieeUtil.ACCOUNT);
            flowieeImport.setStartTime(startTimeImport);
            flowieeImport.setEndTime(new Date());
            flowieeImport.setResult(resultOfFlowieeImport);
            flowieeImport.setDetail(detailOfFlowieeImport);
            flowieeImport.setSuccessRecord(importSuccess);
            flowieeImport.setTotalRecord(totalRecord);
            flowieeImport.setFileId(fileStorageRepository.findByCreatedTime(fileStorage.getCreatedAt()).getId());
            flowieeImportService.save(flowieeImport);

            Notification notification = new Notification();
            notification.setTitle(resultOfFlowieeImport);
            notification.setSend(FlowieeUtil.SYS_NOTI_ID);
            notification.setReceive(FlowieeUtil.ACCOUNT_ID);
            notification.setType(NotificationUtil.NOTI_TYPE_IMPORT);
            notification.setContent(resultOfFlowieeImport);
            notification.setReaded(false);
            notification.setImportId(flowieeImportRepository.findByStartTime(flowieeImport.getStartTime()).getId());
            notificationService.save(notification);

            return TagName.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TagName.SERVICE_RESPONSE_FAIL;
    }

    @Override
    public byte[] exportTemplate() {
        return FileUtil.exportTemplate(FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH);
    }

    @Override
    public byte[] exportData() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                    Path.of(filePathTemp),
                    StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<DonViTinh> listData = this.findAll();
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