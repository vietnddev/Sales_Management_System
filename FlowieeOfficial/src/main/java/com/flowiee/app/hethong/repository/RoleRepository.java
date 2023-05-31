package com.flowiee.app.hethong.repository;

import com.flowiee.app.hethong.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<AccountRole, Integer> {
    List<AccountRole> findByModule(String module);

    @Query("from AccountRole where accountId=:accountId and module=:module and action=:action")
    AccountRole isAuthorized(int accountId, String module, String action);
}