package com.flowiee.app.nguoidung.repository;

import com.flowiee.app.nguoidung.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer>{
    @Query("from AccountEntity a where a.username=:username")
    AccountEntity findByUsername(String username);

    String findUsernameById(int id);

    @Query("select a.id from AccountEntity a where a.username=:username")
    int findIdByUsername(String username);
}