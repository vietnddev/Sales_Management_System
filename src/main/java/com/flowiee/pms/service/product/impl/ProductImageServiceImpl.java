package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.config.StartUp;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.system.FileStorageRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductImageService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.service.sales.TicketImportService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.FileUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductImageServiceImpl extends BaseService implements ProductImageService {
    FileStorageRepository fileRepository;
    TicketExportService   ticketExportService;
    TicketImportService   ticketImportService;
    ProductVariantService productVariantService;

    @Override
    public List<FileStorage> getImageOfProduct(Integer productId) {
        return fileRepository.findAllImages(MODULE.PRODUCT.name(), productId, null);
    }

    @Override
    public List<FileStorage> getImageOfProductVariant(Integer productDetailId) {
        return fileRepository.findAllImages(MODULE.PRODUCT.name(), null, productDetailId);
    }

    @Override
    @Transactional
    public FileStorage saveImageProduct(MultipartFile fileUpload, int pProductId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage(fileUpload, MODULE.PRODUCT.name(), pProductId);
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT.name()));
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    @Transactional
    public FileStorage saveImageProductVariant(MultipartFile fileUpload, int pProductVariantId) throws IOException {
        Optional<ProductVariantDTO> productDetail = productVariantService.findById(pProductVariantId);
        if (productDetail.isEmpty()) {
            throw new BadRequestException();
        }

        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage(fileUpload, MODULE.PRODUCT.name(), productDetail.get().getProductId());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setProductDetail(productDetail.get());
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    public FileStorage saveImageTicketImport(MultipartFile fileUpload, int ticketImportId) throws IOException {
        Optional<TicketImport> ticketImport = ticketImportService.findById(ticketImportId);
        if (ticketImport.isEmpty()) {
            throw new BadRequestException();
        }

        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage(fileUpload, MODULE.STORAGE.name(), null);
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setTicketImport(ticketImport.get());
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.STORAGE) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    public FileStorage saveImageTicketExport(MultipartFile fileUpload, int ticketExportId) throws IOException {
        Optional<TicketExport> ticketExport = ticketExportService.findById(ticketExportId);
        if (ticketExport.isEmpty()) {
            throw new BadRequestException();
        }

        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage(fileUpload, MODULE.STORAGE.name(), null);
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setTicketExport(ticketExport.get());
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.STORAGE) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    public FileStorage setImageActiveOfProduct(Integer pProductId, Integer pImageId) {
        FileStorage imageToActive = fileRepository.findById(pImageId).orElse(null);
        if (imageToActive == null) {
            throw new BadRequestException();
        }
        //Bỏ image default hiện tại
        FileStorage imageActiving = fileRepository.findActiveImage(pProductId, null);
        if (imageActiving != null) {
            imageActiving.setActive(false);
            fileRepository.save(imageActiving);
        }
        //Active lại image theo id được truyền vào
        imageToActive.setActive(true);
        return fileRepository.save(imageToActive);
    }

    @Override
    public FileStorage setImageActiveOfProductVariant(Integer pProductVariantId, Integer pImageId) {
        FileStorage imageToActive = fileRepository.findById(pImageId).orElse(null);
        if (imageToActive == null) {
            throw new BadRequestException();
        }
        //Bỏ image default hiện tại
        FileStorage imageActivating = fileRepository.findActiveImage(null, pProductVariantId);
        if (ObjectUtils.isNotEmpty(imageActivating)) {
            imageActivating.setActive(false);
            fileRepository.save(imageActivating);
        }
        //Active lại image theo id được truyền vào
        imageToActive.setActive(true);
        return fileRepository.save(imageToActive);
    }

    @Override
    public FileStorage findImageActiveOfProduct(int pProductId) {
        return fileRepository.findActiveImage(pProductId, null);
    }

    @Override
    public FileStorage findImageActiveOfProductVariant(int pProductVariantId) {
        return fileRepository.findActiveImage(null, pProductVariantId);
    }

    @Transactional
    @Override
    public FileStorage changeImageProduct(MultipartFile fileAttached, int fileId) {
        Long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        Optional<FileStorage> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            throw new BadRequestException();
        }
        FileStorage fileToChange = fileOptional.get();
        //Delete file vật lý cũ
        try {
            File file = new File(FileUtils.rootPath + "/" + fileToChange.getDirectoryPath() + "/" + fileToChange.getStorageName());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("File not found!", e);
        }
        //Update thông tin file mới
        fileToChange.setOriginalName(fileAttached.getOriginalFilename());
        fileToChange.setCustomizeName(fileAttached.getOriginalFilename());
        fileToChange.setStorageName(currentTime + "_" + fileAttached.getOriginalFilename());
        fileToChange.setFileSize(fileAttached.getSize());
        fileToChange.setExtension(FileUtils.getFileExtension(fileAttached.getOriginalFilename()));
        fileToChange.setContentType(fileAttached.getContentType());
        fileToChange.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")));
        fileToChange.setAccount(new Account(CommonUtils.getUserPrincipal().getId()));
        FileStorage imageSaved = fileRepository.save(fileToChange);

        //Lưu file mới vào thư mục chứa file upload
        try {
            Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + fileAttached.getOriginalFilename());
            fileAttached.transferTo(path);
        } catch (Exception e) {
            logger.error("Lưu file change vào thư mục chứa file upload thất bại! \n" + e.getCause().getMessage(), e);
        }

        return imageSaved;
    }
}