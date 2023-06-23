package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.SystemLog;

import java.util.List;

public interface SystemLogService {
    List<SystemLog> getAll();

    void writeLog(SystemLog log);

    void writeLog(String module, String action, String noiDung, String noiDungCapNhat);
}