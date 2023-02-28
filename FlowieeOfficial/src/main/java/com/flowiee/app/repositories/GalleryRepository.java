package com.flowiee.app.repositories;

import com.flowiee.app.model.storage.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    public List<Gallery> findByProductID(int proructID);
}
