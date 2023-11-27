package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.DocData;

@Repository
public interface DocDataRepository extends JpaRepository<DocData, Integer> {
}