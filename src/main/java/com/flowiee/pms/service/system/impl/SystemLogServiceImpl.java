package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.repository.system.SystemLogRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.SystemLogService;

import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.LogType;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.utils.constants.MasterObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SystemLogServiceImpl extends BaseService implements SystemLogService {
    SystemLogRepository systemLogRepo;

    @Override
    public Page<SystemLog> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        }
        Page<SystemLog> logs = systemLogRepo.findAll(pageable);
        for (SystemLog systemLog : logs.getContent()) {
            systemLog.setAccountName(systemLog.getAccount() != null ? systemLog.getAccount().getFullName() : "");
            systemLog.setContentChange(systemLog.getContentChange() != null ? systemLog.getContentChange() : "");
        }
        return logs;
    }

    @Override
    public SystemLog writeLogCreate(MODULE module, ACTION function, MasterObject object, String title, String content) {
        return this.writeLog(module, function, object, LogType.I, title, content, "-");
    }

    @Override
    public SystemLog writeLogUpdate(MODULE module, ACTION function, MasterObject object, String title, ChangeLog changeLog) {
        return this.writeLog(module, function, object, LogType.U, title, changeLog.getOldValues(), changeLog.getNewValues());
    }

    @Override
    public SystemLog writeLogUpdate(MODULE module, ACTION function, MasterObject object, String title, String content) {
        return this.writeLog(module, function, object, LogType.U, title, content, "-");
    }

    @Override
    public SystemLog writeLogUpdate(MODULE module, ACTION function, MasterObject object, String title, String content, String contentChange) {
        return this.writeLog(module, function, object, LogType.U, title, content, contentChange);
    }

    @Override
    public SystemLog writeLogDelete(MODULE module, ACTION function, MasterObject object, String title, String content) {
        return this.writeLog(module, function, object, LogType.D, title, content, "-");
    }

    @Override
    public SystemLog writeLog(MODULE module, ACTION function, MasterObject object, LogType mode, String title, String content, String contentChange) {
        return systemLogRepo.save(SystemLog.builder()
                .module(module.name())
                .function(function.name())
                .object(object.name())
                .mode(mode.name())
                .title(title)
                .content(content)
                .contentChange(contentChange)
                .ip(CommonUtils.getUserPrincipal().getIp())
                .account(CommonUtils.getUserPrincipal().toEntity())
                .build());
    }
}