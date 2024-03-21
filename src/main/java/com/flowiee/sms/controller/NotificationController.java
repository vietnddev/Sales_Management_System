package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.entity.Notification;
import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.model.ApiResponse;
import com.flowiee.sms.service.NotificationService;
import com.flowiee.sms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/notification")
@Tag(name = "Notification API", description = "Thông báo hệ thống")
public class NotificationController extends BaseController {
    @Autowired private NotificationService notificationService;

    @Operation(summary = "Find all notifications")
    @GetMapping("/all")
    public ApiResponse<List<Notification>> findAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            return ApiResponse.ok(notificationService.findAll());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "notification"));
        }
    }

    @Operation(summary = "Find all notifications")
    @GetMapping("/{accountId}")
    public ApiResponse<List<Notification>> findByAccount(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                         @RequestParam(value = "totalRecord", required = false) Integer totalRecord,
                                                         @PathVariable("accountId") int accountId) {
        try {
            return ApiResponse.ok(notificationService.findAllByReceiveId(pageSize, pageNum, totalRecord, accountId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "notification"));
        }
    }
}