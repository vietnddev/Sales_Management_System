package com.flowiee.pms.repository.system;

import com.flowiee.pms.entity.system.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailStatusRepository extends JpaRepository<MailStatus, Long> {
}