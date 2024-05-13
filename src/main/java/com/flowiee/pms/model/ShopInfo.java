package com.flowiee.pms.model;

import lombok.Data;

@Data
public class ShopInfo {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String description;
    private String returnPolicy;
    private String refundPolicy;
    private String logoUrl;
}