package com.flowiee.app.khotailieu.repository;

import com.flowiee.app.khotailieu.entity.DocData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocDataRepository extends JpaRepository<DocData, Integer> {
}