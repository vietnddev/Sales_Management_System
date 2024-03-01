package com.flowiee.app.service;

import java.util.List;

import com.flowiee.app.entity.SystemLog;
import org.springframework.data.domain.Page;

public interface SystemLogService {
    Page<SystemLog> findAll(int pageSize, int pageNum);

    List<SystemLog> getAll();

    SystemLog writeLog(SystemLog log);

    SystemLog writeLog(String module, String action, String content);

    SystemLog writeLog(String module, String action, String content, String contentChange);
}