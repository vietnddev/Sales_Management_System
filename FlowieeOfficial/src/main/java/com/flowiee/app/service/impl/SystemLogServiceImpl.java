package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.repository.system.SystemLogRepository;
import com.flowiee.app.service.system.SystemLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogRepository logRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<SystemLog> getAll() {
        List<SystemLog> dataReturn = new ArrayList<>();
        String srtSQL = "select l.id, l.module, l.action, l.noi_dung, l.noi_dung_cap_nhat, l.ip, a.username from sys_log l inner join sys_account a on a.id = l.created_by";
        Query result = entityManager.createNativeQuery(srtSQL);
        List<Object[]> listData = result.getResultList();
        for (Object[] data : listData) {
            SystemLog systemLog = new SystemLog();
            systemLog.setId(Integer.parseInt(String.valueOf(data[0])));
            systemLog.setModule(String.valueOf(data[1]));
            systemLog.setAction(String.valueOf(data[2]));
            systemLog.setNoiDung(String.valueOf(data[3]));
            systemLog.setNoiDungCapNhat(String.valueOf(data[4]));
            systemLog.setIp(String.valueOf(data[5]));
            systemLog.setUsername(String.valueOf(data[6]));
            dataReturn.add(systemLog);
        }
        return dataReturn;
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
        systemLog.setCreatedBy(FlowieeUtil.ACCOUNT_ID);
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
        systemLog.setCreatedBy(FlowieeUtil.ACCOUNT_ID);
        systemLog.setIp(FlowieeUtil.ACCOUNT_IP);
        return logRepository.save(systemLog);
    }
}