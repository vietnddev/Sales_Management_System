package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.repository.system.FileStorageRepository;
import com.flowiee.pms.service.sales.OrderQRCodeService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;

@Service
public class OrderQRCodeServiceImpl implements OrderQRCodeService {
    @Autowired
    private FileStorageRepository fileRepository;

    @Override
    public String saveQRCodeOfOrder(int orderId) {
        try {
            long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
            String imageQRCodeName = "qrcode_" + orderId + ".png";
            FileStorage fileInfo = new FileStorage();
            fileInfo.setModule(MODULE.PRODUCT.name());
            fileInfo.setOriginalName(imageQRCodeName);
            fileInfo.setCustomizeName(imageQRCodeName);
            fileInfo.setStorageName(currentTime + "_" + imageQRCodeName);
            //fileInfo.setKichThuocFile();
            fileInfo.setExtension(AppConstants.FILE_EXTENSION.PNG.getLabel());
            fileInfo.setContentType(null);
            fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")));
            fileInfo.setProduct(null);
            fileInfo.setOrder(new Order(orderId));
            fileInfo.setAccount(new Account(CommonUtils.getUserPrincipal().getId()));
            fileInfo.setActive(false);
            fileRepository.save(fileInfo);

            String data = "http://192.168.120.199:8085/don-hang/" + orderId;
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            // Write to file image
            Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + imageQRCodeName);
            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            return "OK";
        } catch (Exception ex) {
            throw new AppException(ex);
        }
    }

    @Override
    public FileStorage findQRCodeOfOrder(int orderId) {
        return fileRepository.findQRCodeOfOrder(orderId);
    }
}