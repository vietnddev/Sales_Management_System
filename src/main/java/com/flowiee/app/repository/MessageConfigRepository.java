package com.flowiee.app.repository;

import com.flowiee.app.entity.MessageConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageConfigRepository extends JpaRepository<MessageConfig, Integer> {
}