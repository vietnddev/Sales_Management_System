package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("from Notification n where n.receive=:accountId order by n.id desc")
    List<Notification> findAllByReceiveId(@Param("accountId") Integer accountId);

    @Query(value = "select * from notification n where n.receive=:accountId order by n.id desc fetch next :limit row only", nativeQuery = true)
    List<Notification> findLimitByReceiveId(@Param("accountId") Integer accountId, @Param("limit") Integer limit);
}