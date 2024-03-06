package com.flowiee.sms.repository;

import com.flowiee.sms.entity.CategoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryHistoryRepository extends JpaRepository<CategoryHistory, Integer> {
    @Modifying
    @Query("delete from CategoryHistory where category.id=:categoryId")
    void deleteAllByCategory(@Param("categoryId") Integer categoryId);
}