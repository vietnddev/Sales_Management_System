package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.repository.system.FileStorageRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.OrderQRCodeService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.FileExtension;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderQRCodeServiceImpl extends BaseService implements OrderQRCodeService {
    FileStorageRepository fileRepository;

    @Override
    public String saveQRCodeOfOrder(int orderId) {
        try {
            long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
            String imageQRCodeName = "qrcode_" + orderId + ".png";
            fileRepository.save(FileStorage.builder()
                    .module(MODULE.PRODUCT.name())
                    .originalName(imageQRCodeName)
                    .customizeName(imageQRCodeName)
                    .storageName(currentTime + "_" + imageQRCodeName)
                    .extension(FileExtension.PNG.getLabel())
                    .directoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")))
                    .order(new Order(orderId))
                    .account(new Account(CommonUtils.getUserPrincipal().getId()))
                    .isActive(false)
                    .build());
            String data = "http://" + CommonUtils.mvServerInfo.ip() + ":" + CommonUtils.mvServerInfo.port() + "/order/" + orderId;
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