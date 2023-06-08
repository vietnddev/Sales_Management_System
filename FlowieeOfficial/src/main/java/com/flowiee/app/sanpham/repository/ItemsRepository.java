package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {
    @Query("from Items i where i.cart.id=:idCart")
    List<Items> findByCartId(int idCart);
}