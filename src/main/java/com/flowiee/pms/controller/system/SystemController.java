package com.flowiee.pms.controller.system;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.entity.system.SystemLog;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.EximModel;
import com.flowiee.pms.service.ExportService;
import com.flowiee.pms.service.system.ConfigService;
import com.flowiee.pms.service.system.SystemLogService;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.TemplateExport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/sys")
@Tag(name = "System API", description = "Quản lý hệ thống")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SystemController extends BaseController {
    SystemLogService logService;
    ConfigService    configService;
    @Qualifier("logExportServiceImpl")
    @NonFinal
    @Autowired
    ExportService    exportService;

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

    @Operation(summary = "Export all log")
    @GetMapping("/log/export")
    @PreAuthorize("@vldModuleSystem.readLog(true)")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        EximModel model = exportService.exportToExcel(TemplateExport.EX_LIST_OF_LOGS, null, false);
        return ResponseEntity.ok().headers(model.getHttpHeaders()).body(model.getContent());
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
    public AppResponse<SystemConfig> updateConfig(@RequestBody SystemConfig config, @PathVariable("id") Long configId) {
        return success(configService.update(config, configId));
    }

    @GetMapping("/refresh")
    public AppResponse<String> refreshApp() {
        return success(configService.refreshApp());
    }
}