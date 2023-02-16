package com.flowiee.app.services;

import com.flowiee.app.model.admin.Log;
import com.flowiee.app.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public List<Log> findAll(){
        return logRepository.findAll();
    }

    public List<Log> findByAction(String action){
        return logRepository.findByAction(action);
    }
}
