package com.flowiee.app.products.repository;

import com.flowiee.app.file.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

//	@Query(value = "SELECT i FROM FileEntity i Where i.productVariant.productVariantID=:productVariantID")
//	public List<FileEntity> getImageByPVariantID(int productVariantID);
               
	@Query(value = "SELECT f FROM FileEntity f INNER JOIN f.productVariant")
	public List<FileEntity> getAllImage();
} 
               