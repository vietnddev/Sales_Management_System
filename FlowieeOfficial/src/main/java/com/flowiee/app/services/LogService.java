package com.flowiee.app.services;

import com.flowiee.app.model.admin.Log;
import com.flowiee.app.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;

    public List<Log> getAll(){
        return logRepository.findAll();
    }

    public List<Log> getByAction(String action){
        return logRepository.findByType(action);
    }

    public void insertLog(Log log){
        logRepository.save(log);
    }
}
