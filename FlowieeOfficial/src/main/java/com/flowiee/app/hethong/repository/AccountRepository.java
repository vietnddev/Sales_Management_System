package com.flowiee.app.hethong.repository;

import com.flowiee.app.hethong.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Query("from Account a where a.username=:username")
    Account findByUsername(String username);

    String findUsernameById(int id);

    @Query("select a.id from Account a where a.username=:username")
    int findIdByUsername(String username);
}