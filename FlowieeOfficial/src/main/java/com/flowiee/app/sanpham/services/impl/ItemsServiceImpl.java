package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.Items;
import com.flowiee.app.sanpham.repository.ItemsRepository;
import com.flowiee.app.sanpham.services.ItemsService;
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
    public List<Items> findByCartId(int cartId) {
        return itemsRepository.findByCartId(cartId);
    }

    @Override
    public Items findById(int id) {
        return itemsRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Items items) {
        itemsRepository.save(items);
        return "OK";
    }

    @Override
    public String delete(int id) {
        itemsRepository.deleteById(id);
        return "OK";
    }

    @Override
    public Integer findSoLuongByBienTheSanPhamId(int bienTheSanPhamId) {
        return itemsRepository.findSoLuongByBienTheSanPhamId(bienTheSanPhamId);
    }
}