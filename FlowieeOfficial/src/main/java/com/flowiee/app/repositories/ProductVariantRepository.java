package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.Product_Variants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository <Product_Variants, Integer>{
    // Lấy danh sách biến thể theo id sản phẩm
    public List<Product_Variants> findByNameAndProductID(String name, int productID);
}
