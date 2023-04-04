package com.flowiee.app.repositories;

import com.flowiee.app.model.Image;
import com.flowiee.app.model.Product_Variants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

	@Query(value = "SELECT i FROM Image i Where i.productVariant.productVariantID=:productVariantID")
	public List<Image> getImageByPVariantID(int productVariantID);
               
	@Query("SELECT f FROM Image f INNER JOIN f.productVariant")
	public List<Image> getAllImage();
} 
               