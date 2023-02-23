package com.flowiee.app.repositories;

import com.flowiee.app.model.storage.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
}
