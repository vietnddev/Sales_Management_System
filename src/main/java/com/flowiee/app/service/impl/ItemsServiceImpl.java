package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Items;
import com.flowiee.app.repository.ItemsRepository;
import com.flowiee.app.service.ItemsService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsServiceImpl implements ItemsService {
    @Autowired
    private ItemsRepository itemsRepository;

    @Override
    public List<Items> findAll() {
        return itemsRepository.findAll();
    }

    @Override
    public List<Items> findByCartId(Integer cartId) {
        return itemsRepository.findByCartId(cartId);
    }

    @Override
    public Items findById(Integer id) {
        return itemsRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Items items) {
        if (items == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        itemsRepository.save(items);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Items entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        itemsRepository.save(entity);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        if (id == null || id <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        Items items = this.findById(id);
        if (items == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        itemsRepository.deleteById(id);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public Integer findSoLuongByBienTheSanPhamId(int bienTheSanPhamId) {
        return itemsRepository.findSoLuongByBienTheSanPhamId(bienTheSanPhamId);
    }
}