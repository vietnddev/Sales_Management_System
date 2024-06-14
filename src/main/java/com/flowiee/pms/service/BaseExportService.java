package com.flowiee.pms.service;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.ExportDataModel;
import com.flowiee.pms.utils.constants.TemplateExport;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.transaction.NotSupportedException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;

public abstract class BaseExportService extends BaseService implements ExportService {
    protected abstract void writeData(Object pCondition);

    protected XSSFWorkbook mvWorkbook;
    protected ExportDataModel mvExportModel;

    @Override
    public ExportDataModel exportToExcel(TemplateExport templateExport, Object pCondition, boolean templateOnly) {
        try {
            mvExportModel = new ExportDataModel(templateExport);
            if (templateOnly) {
                mvWorkbook = new XSSFWorkbook(mvExportModel.getPathSource().toFile());
            } else {
                mvWorkbook = new XSSFWorkbook(Files.copy(mvExportModel.getPathSource(), mvExportModel.getPathTarget(), StandardCopyOption.REPLACE_EXISTING).toFile());
                writeData(pCondition);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mvWorkbook.write(byteArrayOutputStream);
            setFileContent(byteArrayOutputStream);
            setHttpHeaders();
            mvExportModel.setResult("OK");
            return mvExportModel;
        } catch (Exception e) {
            mvExportModel.setResult("NOK");
            throw new AppException("Error when export data!", e);
        } finally {
            try {
                if (mvWorkbook != null) mvWorkbook.close();
                Files.deleteIfExists(mvExportModel.getPathTarget());
                mvExportModel.setFinishTime(LocalTime.now());
            } catch (IOException e) {
                logger.error("Error when export data!", e);
            }
        }
    }

    @SneakyThrows
    @Override
    public ExportDataModel exportToCsv(TemplateExport templateExport, Object pCondition) {
        throw new NotSupportedException();
    }

    private void setHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + mvExportModel.getDefaultOutputName());
        mvExportModel.setHttpHeaders(httpHeaders);
    }

    private void setFileContent(ByteArrayOutputStream byteArrayOS) {
        ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(byteArrayOS.toByteArray());
        InputStreamResource inputStreamResource = new InputStreamResource(byteArrayIS);
        mvExportModel.setContent(inputStreamResource);
    }

    protected void setBorderCell(XSSFRow pRow, int pColFrom, int pColTo) {
        if (pRow == null) return;
        for (int j = pColFrom; j <= pColTo; j++) {
            XSSFCellStyle lvCellStyle = mvWorkbook.createCellStyle();
            lvCellStyle.setBorderTop(BorderStyle.THIN);
            lvCellStyle.setBorderBottom(BorderStyle.THIN);
            lvCellStyle.setBorderLeft(BorderStyle.THIN);
            lvCellStyle.setBorderRight(BorderStyle.THIN);

            pRow.getCell(j).setCellStyle(lvCellStyle);
        }
    }
}