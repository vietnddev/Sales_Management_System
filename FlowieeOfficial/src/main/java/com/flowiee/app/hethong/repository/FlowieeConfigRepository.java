package com.flowiee.app.hethong.repository;

import com.flowiee.app.hethong.entity.CauHinhHeThong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowieeConfigRepository extends JpaRepository<CauHinhHeThong, Integer> {
}