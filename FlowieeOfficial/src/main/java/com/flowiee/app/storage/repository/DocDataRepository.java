package com.flowiee.app.storage.repository;

import com.flowiee.app.storage.entity.DocData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocDataRepository extends JpaRepository<DocData, Integer> {
}