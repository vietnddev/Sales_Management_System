package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.service.BaseExportService;
import com.flowiee.pms.service.sales.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderExportServiceImpl extends BaseExportService {
    OrderService mvOrderService;

    @Override
    protected void writeData(Object pCondition) {
        XSSFSheet sheet = mvWorkbook.getSheetAt(0);

        List<OrderDTO> listData = mvOrderService.findAll();
        for (int i = 0; i < listData.size(); i++) {
            XSSFRow row = sheet.createRow(i + 4);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(listData.get(i).getCode());
            row.createCell(2).setCellValue(listData.get(i).getOrderTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            row.createCell(3).setCellValue(listData.get(i).getSalesChannelName());
            row.createCell(4).setCellValue(String.valueOf(listData.get(i).getTotalAmountDiscount()));
            row.createCell(5).setCellValue(listData.get(i).getPayMethodName());
            row.createCell(6).setCellValue(listData.get(i).getPaymentStatus() ? "Đã thanh toán" : "Chưa thanh toán");
            row.createCell(7).setCellValue(listData.get(i).getCustomerName());
            row.createCell(8).setCellValue(listData.get(i).getReceiverPhone());
            row.createCell(9).setCellValue(listData.get(i).getReceiverAddress());
            row.createCell(10).setCellValue(listData.get(i).getOrderStatusName());
            row.createCell(11).setCellValue(listData.get(i).getNote());

            setBorderCell(row, 0, 11);
        }
    }
}