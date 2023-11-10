package com.flowiee.app.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.storage.DocData;

@Repository
public interface DocDataRepository extends JpaRepository<DocData, Integer> {
}