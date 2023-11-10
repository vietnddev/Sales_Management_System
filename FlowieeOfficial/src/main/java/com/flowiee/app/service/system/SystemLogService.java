package com.flowiee.app.service.system;

import java.util.List;

import com.flowiee.app.entity.system.SystemLog;

public interface SystemLogService {
    List<SystemLog> getAll();

    SystemLog writeLog(SystemLog log);

    SystemLog writeLog(String module, String action, String noiDung);

    SystemLog writeLog(String module, String action, String noiDung, String noiDungCapNhat);
}