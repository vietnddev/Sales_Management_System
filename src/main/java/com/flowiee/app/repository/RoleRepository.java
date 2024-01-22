package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.flowiee.app.entity.AccountRole;

import java.util.List;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<AccountRole, Integer> {
    List<AccountRole> findByModule(String module);

    List<AccountRole> findByAccountId(Integer accountId);

    @Query("select id from AccountRole where accountId=:accountId")
    List<Integer> findIdsByAccountId(@Param("accountId") Integer accountId);

    @Query("from AccountRole where accountId=:accountId and module=:module and action=:action")
    AccountRole isAuthorized(@Param("accountId") Integer accountId, @Param("module") String module, @Param("action") String action);

    @Query("from AccountRole where action=:action and accountId=:accountId")
    AccountRole findByActionAndAccountId(@Param("action") String action, @Param("accountId") Integer accountId);

    @Modifying
    void deleteByAccountId(Integer accountId);
}