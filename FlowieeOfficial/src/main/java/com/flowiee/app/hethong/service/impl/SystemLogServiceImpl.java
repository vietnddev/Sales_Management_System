package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.repository.SystemLogRepository;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogRepository logRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public List<SystemLog> getAll(){
        return logRepository.findAll();
    }

    @Override
    public void writeLog(SystemLog log){
        logRepository.save(log);
    }

    @Override
    public void writeLog(String module, String action, String noiDung, String noiDungCapNhat) {
        SystemLog systemLog = new SystemLog();
        systemLog.setModule(module);
        systemLog.setAction(action);
        systemLog.setNoiDung(noiDung);
        systemLog.setNoiDungCapNhat(noiDungCapNhat);
        systemLog.setAccount(accountService.getCurrentAccount());
        systemLog.setIp(FlowieeUtil.getIPLogin());
        logRepository.save(systemLog);
    }
}