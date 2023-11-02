package com.flowiee.app.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("from Category c where c.code = 'ROOT' order by c.sort")
    List<Category> findRootCategory();

    @Query("from Category c where c.type=:type and (c.code is null or c.code <> 'ROOT') order by c.sort")
    List<Category> findSubCategory(String type);
}