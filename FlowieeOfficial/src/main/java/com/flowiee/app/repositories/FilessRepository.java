package com.flowiee.app.repositories;

import com.flowiee.app.model.storage.Filess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilessRepository extends JpaRepository<Filess, Integer> {
	
    public List<Filess> findByproductVariant(int productVariantID);
               
	@Query("SELECT f FROM Filess f INNER JOIN f.productVariant")
	public List<Filess> getAllFiless();
} 
               