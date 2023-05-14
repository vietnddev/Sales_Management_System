package com.flowiee.app.products.repository;

import com.flowiee.app.products.entity.BienTheSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienTheSanPhamRepository extends JpaRepository <BienTheSanPham, Integer>{
    // Lấy danh sách biến thể theo id sản phẩm
    //public List<BienTheSanPham> findByTenBienTheAndProductID(String name, int productID);
}
