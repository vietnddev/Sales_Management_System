package com.flowiee.app.repositories;

import com.flowiee.app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    Account findByUsername(String username);

    String findUsernameByID(int id);

}