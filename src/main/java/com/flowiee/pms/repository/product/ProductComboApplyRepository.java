package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductComboApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductComboApplyRepository extends JpaRepository<ProductComboApply, Integer> {
    @Query("from ProductComboApply p where p.comboId = :comboId")
    List<ProductComboApply> findByComboId(@Param("comboId") Integer comboId);
}