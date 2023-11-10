package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.repository.system.SystemLogRepository;
import com.flowiee.app.service.system.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogRepository logRepository;

    @Override
    public List<SystemLog> getAll() {
        return logRepository.findAll();
    }

    @Override
    public SystemLog writeLog(SystemLog log) {
        return logRepository.save(log);
    }

    @Override
    public SystemLog writeLog(String module, String action, String noiDung) {
        Account account = FlowieeUtil.ACCOUNT;

        SystemLog systemLog = new SystemLog();
        systemLog.setModule(module);
        systemLog.setAction(action);
        systemLog.setNoiDung(noiDung);
        systemLog.setNoiDungCapNhat(null);
        systemLog.setAccount(new Account(account.getId(), account.getUsername(), account.getHoTen()));
        systemLog.setIp(FlowieeUtil.ACCOUNT_IP);
        return logRepository.save(systemLog);
    }

    @Override
    public SystemLog writeLog(String module, String action, String noiDung, String noiDungCapNhat) {
        Account account = FlowieeUtil.ACCOUNT;

        SystemLog systemLog = new SystemLog();
        systemLog.setModule(module);
        systemLog.setAction(action);
        systemLog.setNoiDung(noiDung);
        systemLog.setNoiDungCapNhat(noiDungCapNhat);
        systemLog.setAccount(new Account(account.getId(), account.getUsername(), account.getHoTen()));
        systemLog.setIp(FlowieeUtil.ACCOUNT_IP);
        return logRepository.save(systemLog);
    }
}