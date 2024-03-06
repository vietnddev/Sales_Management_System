package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    @Query("from Account a where a.username=:username")
    Account findByUsername(@Param("username") String username);
}