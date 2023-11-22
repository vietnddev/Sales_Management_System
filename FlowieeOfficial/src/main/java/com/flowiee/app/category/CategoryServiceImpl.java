package com.flowiee.app.category;

import com.flowiee.app.model.system.SystemModule;
import com.flowiee.app.utils.*;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.FlowieeImport;
import com.flowiee.app.entity.Notification;
import com.flowiee.app.repository.FileStorageRepository;
import com.flowiee.app.repository.FlowieeImportRepository;
import com.flowiee.app.service.OrderPayService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.ProductVariantService;
import com.flowiee.app.service.DocumentService;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.FlowieeImportService;
import com.flowiee.app.service.NotificationService;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class CategoryServiceImpl implements CategoryService {
	private static final String MODULE = SystemModule.DANH_MUC.name();
	
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private DocumentService documentService;
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
    @Autowired
    private AccountService accountService;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer entityId) {
        return categoryRepository.findById(entityId).orElse(null);
    }

    @Override
    public String save(Category entity) {
        if (entity == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        categoryRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Transactional
    @Override
    public String update(Category entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        categoryRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Transactional
    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0 || this.findById(entityId) == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        categoryRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<Category> findRootCategory() {
        return categoryRepository.findRootCategory();
    }

    @Override
    public List<Category> findSubCategory(String categoryType) {
        return categoryRepository.findSubCategory(categoryType);
    }

    @Override
    public Category findSubCategoryDefault(String categoryType) {
        return categoryRepository.findSubCategoryDefault(categoryType);
    }

    @Override
    public List<Category> findSubCategoryUnDefault(String categoryType) {
        return categoryRepository.findSubCategoryUnDefault(categoryType);
    }

    @Override
    public Boolean categoryInUse(Integer categoryId) {
        Category category = this.findById(categoryId);
        if (category.getType().equals(AppConstants.UNIT)) {
            if (!productService.findByProductType(categoryId).isEmpty()) {
                return true;
            } else if (!productService.findByUnit(categoryId).isEmpty()) {
                return true;
            } else if (!productService.findByBrand(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.FABRICTYPE)) {
            if (!productVariantService.findByFabricType(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.PAYMETHOD)) {
            if (!orderPayService.findByPayMethod(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.SALESCHANNEL)) {
            if (!orderService.findBySalesChannel(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.SIZE)) {
            if (!productVariantService.findBySize(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.COLOR)) {
            if (!productVariantService.findByColor(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.PRODUCTTYPE)) {
            if (!productService.findByProductType(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.DOCUMENTTYPE)) {
            if (!documentService.findByDoctype(categoryId).isEmpty()) {
                return true;
            }
        } else if (category.getType().equals(AppConstants.ORDERSTATUS)) {
            if (!orderService.findByOrderStatus(categoryId).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Transactional
	@Override
	public String importData(MultipartFile fileImport, String categoryType) {
		Date startTimeImport = new Date();
        String resultOfFlowieeImport = "";
        String detailOfFlowieeImport = "";
        int importSuccess = 0;
        int totalRecord = 0;
        boolean isImportSuccess = true;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(fileImport.getInputStream());
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
                        row.getCell(1).setCellStyle(FlowieeUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(2).setCellStyle(FlowieeUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(3).setCellStyle(FlowieeUtil.highlightDataImportEror(cellStyle, fontStyle));
                        continue;
                    }

                    Category category = new Category();
                    category.setType(categoryType);
                    category.setCode(!categoryCode.isEmpty() ? categoryCode : FlowieeUtil.getMaDanhMuc(categoryName));
                    category.setName(categoryName);
                    category.setNote(categoryNote);

                    if (!"OK".equals(this.save(category))) {
                        isImportSuccess = false;
                        XSSFCellStyle cellStyle = workbook.createCellStyle();
                        XSSFFont fontStyle = workbook.createFont();
                        row.getCell(1).setCellStyle(FlowieeUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(2).setCellStyle(FlowieeUtil.highlightDataImportEror(cellStyle, fontStyle));
                        row.getCell(3).setCellStyle(FlowieeUtil.highlightDataImportEror(cellStyle, fontStyle));
                    } else {
                        importSuccess++;
                    }
                    totalRecord++;
                }
            }
            workbook.close();

            if (isImportSuccess) {
                resultOfFlowieeImport = MessagesUtil.IMPORT_DM_DONVITINH_SUCCESS;
                detailOfFlowieeImport = importSuccess + " / " + totalRecord;
            } else {
                resultOfFlowieeImport = MessagesUtil.IMPORT_DM_DONVITINH_FAIL;
                detailOfFlowieeImport = importSuccess + " / " + totalRecord;
            }
            //Save file attach to storage
            FileStorage fileStorage = new FileStorage(fileImport, SystemModule.DANH_MUC.name());
            fileStorage.setGhiChu("IMPORT");
            fileStorage.setStatus(false);
            fileStorage.setActive(false);
            fileStorage.setAccount(accountService.findCurrentAccount());
            fileStorageService.saveFileOfImport(fileImport, fileStorage);

            //Save import
            FlowieeImport flowieeImport = new FlowieeImport();
            flowieeImport.setModule(MODULE);
            flowieeImport.setEntity(Category.class.getName());
            flowieeImport.setAccount(accountService.findCurrentAccount());
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
            notification.setReceive(accountService.findCurrentAccountId());
            notification.setType(MessagesUtil.NOTI_TYPE_IMPORT);
            notification.setContent(resultOfFlowieeImport);
            notification.setReaded(false);
            notification.setImportId(flowieeImportRepository.findByStartTime(flowieeImport.getStartTime()).getId());
            notificationService.save(notification);

            return AppConstants.SERVICE_RESPONSE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppConstants.SERVICE_RESPONSE_FAIL;
	}

	@Override
	public byte[] exportTemplate(String categoryType) {
		return FlowieeUtil.exportTemplate(AppConstants.TEMPLATE_IE_DM_CATEGORY);
	}

	@Override
	public byte[] exportData(String categoryType) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        String filePathOriginal = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + AppConstants.TEMPLATE_IE_DM_CATEGORY + ".xlsx";
        String filePathTemp = FlowieeUtil.PATH_TEMPLATE_EXCEL + "/" + AppConstants.TEMPLATE_IE_DM_CATEGORY + "_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        File fileDeleteAfterExport = new File(Path.of(filePathTemp).toUri());
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
								                     Path.of(filePathTemp),
								                     StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<Category> listData = this.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getCode());
                row.createCell(2).setCellValue(listData.get(i).getName());
                row.createCell(3).setCellValue(listData.get(i).getNote());
                for (int j = 0; j <= 3; j++) {
                    row.getCell(j).setCellStyle(FlowieeUtil.setBorder(workbook.createCellStyle()));
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