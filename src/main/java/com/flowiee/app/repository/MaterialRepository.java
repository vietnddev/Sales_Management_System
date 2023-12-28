package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("from Material m " +
           "where (:ticketImportId is null or m.ticketImportGoods.id=:ticketImportId) " +
           "and (:supplierId is null or m.supplier.id=:supplierId) " +
           "and (:unitId is null or m.unit.id=:unitId) " +
           "and (:code is null or m.code=:code) " +
           "and (:name is null or m.name like %:name%) " +
           "and (:location is null or m.location like %:location%) " +
           "and (:statuc is null or m.status=:status)")
    List<Material> findAll(Integer ticketImportId, Integer supplierId, Integer unitId, String code, String name, String location, String status);

    @Query("from Material m where m.code=:code")
    List<Material> findByCode(String code);

    @Query("from Material m where m.ticketImportGoods.id=:importId")
    List<Material> findByImportId(Integer importId);

    @Query("from Material m where m.unit.id=:unitId")
    List<Material> findByUnit(Integer unitId);
}