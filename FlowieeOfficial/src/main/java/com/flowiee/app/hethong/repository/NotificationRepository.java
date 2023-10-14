package com.flowiee.app.hethong.repository;

import com.flowiee.app.hethong.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("from Notification n where n.receive=:accountId order by n.id desc")
    List<Notification> findAllByReceiveId(Integer accountId);

    @Query(value = "select * from notification n where n.receive=:accountId order by n.id desc fetch next :limit row only", nativeQuery = true)
    List<Notification> findLimitByReceiveId(Integer accountId, Integer limit);
}