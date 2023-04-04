package com.flowiee.app.repositories;

import com.flowiee.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "Select * from Category Where Code =  '' :code and Type = '' :type", nativeQuery = true)
    public List<Category> findByCodeAndType(@Param("code") String code, @Param("type") String type);

    public List<Category> findByType(String type);

    @Query(value = "Select Name from Category Where Code =  '' :code and Type = '' :type", nativeQuery = true)
    public String findNameItem(String code, String type);
}
