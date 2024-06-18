package com.flowiee.pms.service;

import com.flowiee.pms.entity.system.FileImportHistory;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.EximModel;
import com.flowiee.pms.repository.system.AppImportRepository;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.TemplateExport;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

public abstract class BaseImportService extends BaseService implements ImportService {
    protected abstract void writeData();

    @Autowired
    private AppImportRepository mvFileImportRepository;
    protected XSSFWorkbook      mvWorkbook;
    protected EximModel         mvEximModel;
    protected FileImportHistory mvFileImportHistory;

    @Transactional
    @Override
    public EximModel importFromExcel(TemplateExport templateExport, MultipartFile multipartFile) {
        try {
            mvEximModel = new EximModel(templateExport);
            mvWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            writeData();

            Path path = Paths.get(CommonUtils.getPathDirectory(templateExport.getModule()) + "/" + mvEximModel.getBeginTime().toNanoOfDay() + "_" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(path);

            LocalTime finishTime = LocalTime.now();
            mvFileImportHistory = new FileImportHistory();
            mvFileImportHistory.setModule(templateExport.getModule().name());
            mvFileImportHistory.setEntity(templateExport.getEntity());
            mvFileImportHistory.setBeginTime(mvEximModel.getBeginTime());
            mvFileImportHistory.setFinishTime(finishTime);
            mvFileImportHistory.setFilePath(path.toString());
            mvFileImportHistory.setAccount(CommonUtils.getUserPrincipal().toAccountEntity());
            mvFileImportRepository.save(mvFileImportHistory);

            mvEximModel.setResult("OK");
            return mvEximModel;
        } catch (Exception e) {
            mvEximModel.setResult("NOK");
            throw new AppException("Error when import data!", e);
        } finally {
            try {
                if (mvWorkbook != null) mvWorkbook.close();
                Files.deleteIfExists(mvEximModel.getPathTarget());
                mvEximModel.setFinishTime(mvFileImportHistory.getFinishTime());
            } catch (IOException e) {
                logger.error("Error when import data!", e);
            }
        }
    }
}