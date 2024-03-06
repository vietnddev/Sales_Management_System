package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.GarmentFactory;

import java.util.List;

public interface GarmentFactoryService extends BaseService<GarmentFactory> {
    List<GarmentFactory> findAll();
}