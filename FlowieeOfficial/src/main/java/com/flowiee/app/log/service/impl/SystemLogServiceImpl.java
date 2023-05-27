package com.flowiee.app.log.service.impl;

import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.log.repository.SystemLogRepository;
import com.flowiee.app.log.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogRepository logRepository;

    @Override
    public List<SystemLog> getAll(){
        return logRepository.findAll();
    }

    @Override
    public void writeLog(SystemLog log){
        logRepository.save(log);
    }
}
