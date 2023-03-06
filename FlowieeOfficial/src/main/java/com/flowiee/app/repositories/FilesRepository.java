package com.flowiee.app.repositories;

import com.flowiee.app.model.storage.Gallery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilesRepository extends JpaRepository<Gallery, Integer> {
    public List<Gallery> findByProductVariantID(int proructID);
}
