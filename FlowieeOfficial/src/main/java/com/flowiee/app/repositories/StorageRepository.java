package com.flowiee.app.repositories;

import com.flowiee.app.model.storage.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Integer> {
    String SQL_GetRootDoc = "Select Storage.* "
            + "From Storage "
            + "Left Join Docshare On Storage.StorageID = Docshare.StorageID "
            + "Where Storage.IDParent = :IDParent and " + "(Docshare.IDUser = :IDUser"
    		+ " or Storage.Author = :IDUser)";
    @Query(value = SQL_GetRootDoc, nativeQuery = true)
    public List<Storage> getRootDoc(@Param("IDParent") int IDParent, @Param("IDUser") int IDUser);
}
