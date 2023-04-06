package com.flowiee.app.services;

import com.flowiee.app.model.PriceHistory;
import com.flowiee.app.repositories.PriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceHistoryService {

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public void save(PriceHistory priceHistory) {
        priceHistoryRepository.save(priceHistory);
    }
}
