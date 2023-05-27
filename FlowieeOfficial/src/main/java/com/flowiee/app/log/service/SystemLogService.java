package com.flowiee.app.log.service;

import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.log.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SystemLogService {

    List<SystemLog> getAll();

    void writeLog(SystemLog log);
}