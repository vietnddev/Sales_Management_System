package com.flowiee.app.khotailieu.repository;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.khotailieu.entity.DocField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocFieldRepository extends JpaRepository<DocField, Integer> {
    @Query("from DocField d where d.loaiTaiLieu=:loaiTaiLieu order by d.sapXep")
    List<DocField> findByLoaiTaiLieu(LoaiTaiLieu loaiTaiLieu);
}