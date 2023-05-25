package com.flowiee.app.nguoidung.repository;

import com.flowiee.app.nguoidung.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<TaiKhoan, Integer>{
    @Query("from TaiKhoan a where a.username=:username")
    TaiKhoan findByUsername(String username);

    String findUsernameById(int id);

    @Query("select a.id from TaiKhoan a where a.username=:username")
    int findIdByUsername(String username);
}