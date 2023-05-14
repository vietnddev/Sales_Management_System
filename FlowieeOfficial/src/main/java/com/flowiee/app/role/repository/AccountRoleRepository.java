package com.flowiee.app.role.repository;

import com.flowiee.app.role.entity.AccountRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRoleEntity, Integer> {
    List<AccountRoleEntity> findByModule(String module);

    @Query("from AccountRoleEntity where accountId=:accountId and module=:module and action=:action")
    AccountRoleEntity isAuthorized(int accountId, String module, String action);
}