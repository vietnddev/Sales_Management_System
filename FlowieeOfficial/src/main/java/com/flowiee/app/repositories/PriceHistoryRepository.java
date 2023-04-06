package com.flowiee.app.repositories;

import com.flowiee.app.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
}
