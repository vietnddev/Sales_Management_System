package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.GarmentFactory;

import java.util.List;

public interface GarmentFactoryService extends BaseService<GarmentFactory> {
    List<GarmentFactory> findAll();
}