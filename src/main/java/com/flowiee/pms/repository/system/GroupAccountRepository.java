package com.flowiee.pms.repository.system;

import com.flowiee.pms.entity.system.GroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAccountRepository extends JpaRepository<GroupAccount, Integer> {
}