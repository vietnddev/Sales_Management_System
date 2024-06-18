package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.system.ConfigService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.constants.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/sys")
@Tag(name = "System API", description = "Quản lý hệ thống")
public class SystemController extends BaseController {
    private final SystemLogService logService;
    private final ConfigService    configService;

    public SystemController(SystemLogService logService, ConfigService configService) {
        this.logService = logService;
        this.configService = configService;
    }

    @Operation(summary = "Find all log")
    @GetMapping("/log/all")
    @PreAuthorize("@vldModuleSystem.readLog(true)")
    public AppResponse<List<SystemLog>> findLogs(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        try {
            Page<SystemLog> logPage = logService.findAll(pageSize, pageNum - 1);
            return success(logPage.getContent(), pageNum, pageSize, logPage.getTotalPages(), logPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "system log"), ex);
        }
    }

    @Operation(summary = "Find all configs")
    @GetMapping("/config/all")
    @PreAuthorize("@vldModuleSystem.readConfig(true)")
    public AppResponse<List<SystemConfig>> findConfigs() {
        try {
            return success(configService.findAll());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "configs"), ex);
        }
    }

    @Operation(summary = "Update config")
    @PutMapping("/config/update/{id}")
    @PreAuthorize("@vldModuleSystem.updateConfig(true)")
    public AppResponse<SystemConfig> updateConfig(@RequestBody SystemConfig config, @PathVariable("id") Integer configId) {
        return success(configService.update(config, configId));
    }

    @GetMapping("/refresh")
    public AppResponse<String> refreshApp() {
        return success(configService.refreshApp());
    }
}