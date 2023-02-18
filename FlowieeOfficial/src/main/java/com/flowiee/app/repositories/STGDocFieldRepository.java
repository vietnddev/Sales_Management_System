package com.flowiee.app.repositories;

import com.flowiee.app.model.storage.STGDocField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface STGDocFieldRepository extends JpaRepository<STGDocField, Integer> {
    public List<STGDocField> findByidDocType(int IDDocType);
}

