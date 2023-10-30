package com.flowiee.app.sanpham.model;

import lombok.Data;
import java.util.Date;

@Data
public class GoodsImportRequest {
    private Integer id;
    private String title;
    private Integer supplierId;
    private Float discount;
    private Integer paymentMethodId;
    private Float paidAmount;
    private String paidStatus;
    private Date orderTime;
    private Date receivedTime;
    private Integer receivedBy;
    private String note;
    private String status;
}