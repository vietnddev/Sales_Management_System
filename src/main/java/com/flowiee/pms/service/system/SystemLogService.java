package com.flowiee.pms.service.system;

import java.util.Map;

import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.utils.constants.LogType;
import org.springframework.data.domain.Page;

public interface SystemLogService {
    Page<SystemLog> findAll(int pageSize, int pageNum);

    SystemLog writeLogCreate(String module, String function, String object, String title, String content);

    SystemLog writeLogUpdate(String module, String function, String object, String title, Map<String, Object[]> logChanges);

    SystemLog writeLogUpdate(String module, String function, String object, String title, String content);

    SystemLog writeLogUpdate(String module, String function, String object, String title, String content, String contentChange);

    SystemLog writeLogDelete(String module, String function, String object, String title, String content);

    SystemLog writeLog(String module, String function, String object, LogType mode, String title, String content, String contentChange);
}