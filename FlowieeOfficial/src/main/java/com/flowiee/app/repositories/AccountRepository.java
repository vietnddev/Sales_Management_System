package com.flowiee.app.repositories;

import com.flowiee.app.model.admin.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
