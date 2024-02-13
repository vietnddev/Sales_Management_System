package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("from Material m " +
           "where (:ticketImportId is null or m.ticketImport.id=:ticketImportId) " +
           "and (:supplierId is null or m.supplier.id=:supplierId) " +
           "and (:unitId is null or m.unit.id=:unitId) " +
           "and (:code is null or m.code=:code) " +
           "and (:name is null or m.name like %:name%) " +
           "and (:location is null or m.location like %:location%) " +
           "and (:statuc is null or m.status=:status)")
    List<Material> findAll(@Param("ticketImportId") Integer ticketImportId, @Param("supplierId") Integer supplierId,
                           @Param("unitId") Integer unitId, @Param("code") String code, @Param("name") String name,
                           @Param("location") String location, @Param("status") String status);

    @Query("from Material m where m.code=:code")
    List<Material> findByCode(@Param("code") String code);

    @Query("from Material m where m.ticketImport.id=:importId")
    List<Material> findByImportId(@Param("importId") Integer importId);

    @Query("from Material m where m.unit.id=:unitId")
    List<Material> findByUnit(@Param("unitId") Integer unitId);

    @Modifying
    @Query("update Material m set m.quantity = (m.quantity + :quantity) where m.id=:materialId")
    void updateQuantityIncrease(@Param("quantity") Integer quantity, @Param("materialId") Integer materialId);

    @Modifying
    @Query("update Material m set m.quantity = (m.quantity - :quantity) where m.id=:materialId")
    void updateQuantityDecrease(@Param("quantity") Integer quantity, @Param("materialId") Integer materialId);
}