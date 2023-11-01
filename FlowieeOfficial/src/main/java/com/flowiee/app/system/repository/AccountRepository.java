package com.flowiee.app.system.repository;

import com.flowiee.app.system.entity.Account;
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