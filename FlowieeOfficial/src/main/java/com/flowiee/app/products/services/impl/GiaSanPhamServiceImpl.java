package com.flowiee.app.products.services.impl;

import com.flowiee.app.products.entity.GiaSanPham;
import com.flowiee.app.products.repository.GiaSanPhamRepository;
import com.flowiee.app.products.services.GiaSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaSanPhamServiceImpl implements GiaSanPhamService {
    @Autowired
    private GiaSanPhamRepository priceHistoryRepository;

    @Override
    public void save(GiaSanPham priceHistory) {
        priceHistoryRepository.save(priceHistory);
    }

    @Override
    public List<GiaSanPham> getListPriceByPVariantID(int pVariantID){
        return priceHistoryRepository.findByproductVariantID(pVariantID);
    }
}
