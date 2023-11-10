package com.flowiee.app.model.product;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherRequest {
    private String title;
    private String description;
    private String doiTuongApDung;
    private String voucherType;
    private Integer lengthOfKey;
    private Integer discount;
    private Float maxPriceDiscount;
    private Date startTime;
    private Date endTime;
    private boolean status;
}