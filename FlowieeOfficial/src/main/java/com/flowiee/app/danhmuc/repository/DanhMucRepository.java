package com.flowiee.app.danhmuc.repository;

import com.flowiee.app.danhmuc.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, Integer> {
    @Query("From DanhMuc dm Where dm.maDanhMuc=:code and dm.loaiDanhMuc=:type")
    List<DanhMuc> findByCodeAndType(String code, String type);

    @Query("From DanhMuc dm Where dm.loaiDanhMuc=:type")
    List<DanhMuc> findByType(String type);

    @Query("Select dm.tenDanhMuc from DanhMuc dm Where dm.maDanhMuc=:code and dm.loaiDanhMuc=:type")
    String findNameItem(String code, String type);
}
