package com.flowiee.app.repositories;

import com.flowiee.app.model.Product_Variants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository <Product_Variants, Integer>{
    // Lấy danh sách biến thể theo id sản phẩm
    public List<Product_Variants> findByNameAndProductID(String name, int productID);
}
