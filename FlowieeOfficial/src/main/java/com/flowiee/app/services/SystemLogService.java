package com.flowiee.app.services;

import com.flowiee.app.model.SystemLog;
import com.flowiee.app.repositories.SystemLogRepository;
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