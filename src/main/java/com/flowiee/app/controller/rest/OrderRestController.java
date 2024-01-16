package com.flowiee.app.controller.rest;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.api.prefix}/order")
@Tag(name = "Order API", description = "Quản lý đơn hàng")
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create new order")
    @PostMapping("/insert")
    public ApiResponse<String> createOrder(@RequestBody OrderDTO orderRequest) {
        try {
            return ApiResponse.ok(orderService.saveOrder(orderRequest));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "order"));
        }
    }
}