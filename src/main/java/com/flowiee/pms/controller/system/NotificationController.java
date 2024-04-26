package com.flowiee.pms.controller.system;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.system.Notification;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.system.NotificationService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/notification")
@Tag(name = "Notification API", description = "Thông báo hệ thống")
public class NotificationController extends BaseController {
    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Find all notifications")
    @GetMapping("/all")
    public AppResponse<List<Notification>> findAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            return success(notificationService.findAll());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "notification"), ex);
        }
    }

    @Operation(summary = "Find all notifications")
    @GetMapping("/{accountId}")
    public AppResponse<List<Notification>> findByAccount(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "totalRecord", required = false) Integer totalRecord,
                                                         @PathVariable("accountId") int accountId) {
        try {
            return success(notificationService.findAllByReceiveId(pageSize, pageNum, totalRecord, accountId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "notification"), ex);
        }
    }
}