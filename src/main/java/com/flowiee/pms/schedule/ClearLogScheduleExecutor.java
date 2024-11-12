package com.flowiee.pms.schedule;

import com.flowiee.pms.entity.system.EventLog;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.repository.system.ConfigRepository;
import com.flowiee.pms.repository.system.EventLogRepository;
import com.flowiee.pms.repository.system.SystemLogRepository;
import com.flowiee.pms.utils.constants.ConfigCode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClearLogScheduleExecutor extends ScheduleExecutor {
    private final SystemLogRepository systemLogRepository;
    private final EventLogRepository eventLogRepository;
    private final ConfigRepository configRepository;

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?")
    @Override
    public void execute() throws AppException {
        logger.info("ClearLogScheduleExecutor start");

        if (!isEnableDeleteLog()) {
            logger.info("ClearLogScheduleExecutor config " + ConfigCode.deleteSystemLog.name() + " is disable");
            return;
        }

        SystemConfig lvDayDeleteSystemLogConfig = configRepository.findByCode(ConfigCode.dayDeleteSystemLog.name());
        if (lvDayDeleteSystemLogConfig == null || lvDayDeleteSystemLogConfig.getValue() == null || ObjectUtils.isEmpty(lvDayDeleteSystemLogConfig.getValue())) {
            logger.info("ClearLogScheduleExecutor config " + ConfigCode.dayDeleteSystemLog.name() + " is disable");
            return;
        }

        int lvDayDeleteSystemLog = Integer.parseInt(lvDayDeleteSystemLogConfig.getValue());
        LocalDateTime lvFromCreatedTime = LocalDateTime.now().minusDays(lvDayDeleteSystemLog).withNano(0).withSecond(0).withMinute(0).withHour(0);

        List<EventLog> eventLogList = eventLogRepository.getEventLogFrom(lvFromCreatedTime);
        for (EventLog eventLog : eventLogList) {
            eventLogRepository.deleteById(eventLog.getRequestId());
        }
        logger.info("ClearLogScheduleExecutor has completed clearing the event log");

        List<SystemLog> systemLogList = systemLogRepository.getSystemLogFrom(lvFromCreatedTime);
        for (SystemLog systemLog : systemLogList) {
            systemLogRepository.deleteById(systemLog.getId());
        }
        logger.info("ClearLogScheduleExecutor has completed clearing the system log");

        //clear more logs here

        logger.info("ClearLogScheduleExecutor end");
    }

    private boolean isEnableDeleteLog() {
        SystemConfig lvDeleteSystemLogConfig = configRepository.findByCode(ConfigCode.deleteSystemLog.name());
        if (lvDeleteSystemLogConfig == null) {
            return false;
        }
        if (lvDeleteSystemLogConfig.getValue() == null) {
            return false;
        }
        if (!"Y".equals(lvDeleteSystemLogConfig.getValue().trim())) {
            return false;
        }
        return true;
    }
}