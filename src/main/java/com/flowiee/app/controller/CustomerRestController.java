package com.flowiee.app.controller;

import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.utils.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/customer")
@Tag(name = "Customer API", description = "Quản lý khách hàng")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Find all")
    @GetMapping("/all")
    public ApiResponse<List<CustomerDTO>> findCustomers() {
        try {
            List<CustomerDTO> result = customerService.findAllCustomer();
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "customer"));
        }
    }
}