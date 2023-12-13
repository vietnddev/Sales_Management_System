package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.DocData;

import java.util.List;

@Repository
public interface DocDataRepository extends JpaRepository<DocData, Integer> {
    @Query("from DocData d where d.docField.id=:docFieldId")
    List<DocData> findByDocField(Integer docFieldId);
}