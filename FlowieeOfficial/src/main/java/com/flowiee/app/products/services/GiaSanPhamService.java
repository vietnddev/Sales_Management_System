package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.GiaSanPham;
import com.flowiee.app.products.repository.GiaSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiaSanPhamService {

    @Autowired
    private GiaSanPhamRepository priceHistoryRepository;

    public void save(GiaSanPham priceHistory) {
        priceHistoryRepository.save(priceHistory);
    }

    public List<GiaSanPham> getListPriceByPVariantID(int pVariantID){
        return priceHistoryRepository.findByproductVariantID(pVariantID);
    }
}
