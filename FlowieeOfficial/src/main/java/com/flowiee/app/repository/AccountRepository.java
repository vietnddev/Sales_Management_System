package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Query("from Account a where a.username=:username")
    Account findByUsername(String username);

    String findUsernameById(int id);

    @Query("select a.id from Account a where a.username=:username")
    int findIdByUsername(String username);
}