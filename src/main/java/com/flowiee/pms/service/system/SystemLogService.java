package com.flowiee.pms.service.system;

import java.util.List;

import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.utils.constants.LogType;
import org.springframework.data.domain.Page;

public interface SystemLogService {
    Page<SystemLog> findAll(int pageSize, int pageNum);

    List<SystemLog> getAll();

    SystemLog writeLog(String module, String function, String object, String mode, String content);

    SystemLog writeLog(String module, String function, String object, String mode, String content, String contentChange);
}