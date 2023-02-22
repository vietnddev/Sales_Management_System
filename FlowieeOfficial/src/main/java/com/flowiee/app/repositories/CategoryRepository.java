package com.flowiee.app.repositories;

import com.flowiee.app.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "Select * from Category Where Code =  '' :code and Type = :type", nativeQuery = true)
    public List<Category> findByCodeAndType(@Param("code") String code, @Param("type") int type);
}
