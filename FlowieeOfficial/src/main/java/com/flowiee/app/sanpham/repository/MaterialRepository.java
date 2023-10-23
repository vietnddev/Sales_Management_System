package com.flowiee.app.sanpham.repository;

import com.flowiee.app.storage.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("from Material m where m.code=:code")
    List<Material> findByCode(String code);
}