package com.flowiee.app.sanpham.model;

import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.sanpham.entity.Supplier;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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