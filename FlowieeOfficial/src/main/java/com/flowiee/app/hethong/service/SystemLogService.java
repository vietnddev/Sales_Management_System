package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.SystemLog;

import java.util.List;

public interface SystemLogService {
    List<SystemLog> getAll();

    SystemLog writeLog(SystemLog log);

    SystemLog writeLog(String module, String action, String noiDung);

    SystemLog writeLog(String module, String action, String noiDung, String noiDungCapNhat);
}