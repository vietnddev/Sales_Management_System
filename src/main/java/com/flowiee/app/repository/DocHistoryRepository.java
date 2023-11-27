package com.flowiee.app.repository;

import com.flowiee.app.entity.DocHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocHistoryRepository extends JpaRepository<DocHistory, Integer> {
    @Query("from DocHistory d where d.document.id=:documentId")
    List<DocHistory> findByDocument(Integer documentId);

    @Query("from DocHistory d where d.docData.id=:docDataId")
    List<DocHistory> findByDocData(Integer docDataId);
}