package com.flowiee.app.log.service;

import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.log.repository.SystemLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogService {
    @Autowired
    private SystemLogRepository logRepository;

    public List<SystemLog> getAll(){
        return logRepository.findAll();
    }

    public void writeLog(SystemLog log){
        logRepository.save(log);
    }
}