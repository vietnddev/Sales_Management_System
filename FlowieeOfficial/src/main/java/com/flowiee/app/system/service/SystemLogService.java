package com.flowiee.app.system.service;

import com.flowiee.app.system.entity.SystemLog;

import java.util.List;

public interface SystemLogService {
    List<SystemLog> getAll();

    SystemLog writeLog(SystemLog log);

    SystemLog writeLog(String module, String action, String noiDung);

    SystemLog writeLog(String module, String action, String noiDung, String noiDungCapNhat);
}