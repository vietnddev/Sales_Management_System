package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.repository.SystemLogRepository;
import com.flowiee.app.hethong.service.SystemLogService;
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
