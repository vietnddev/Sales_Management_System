package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/customer")
@Tag(name = "Customer API", description = "Quản lý khách hàng")
public class CustomerController extends BaseController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Find all customers")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public AppResponse<List<CustomerDTO>> findCustomers(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                        @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                        @RequestParam(value = "name", required = false) String pName,
                                                        @RequestParam(value = "sex", required = false) String pSex,
                                                        @RequestParam(value = "birthday", required = false) Date pBirthday,
                                                        @RequestParam(value = "phone", required = false) String pPhone,
                                                        @RequestParam(value = "email", required = false) String pEmail,
                                                        @RequestParam(value = "address", required = false) String pAddress) {
        try {
            if (pageSize == null) pageSize = -1;
            if (pageNum == null) pageNum = 1;
            Page<CustomerDTO> customers = customerService.findAll(pageSize, pageNum - 1, pName, pSex, pBirthday, pPhone, pEmail, pAddress);
            return success(customers.getContent(), pageNum, pageSize, customers.getTotalPages(), customers.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "customer"), ex);
        }
    }

    @Operation(summary = "Find detail customer")
    @GetMapping("/{customerId}")
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public AppResponse<CustomerDTO> findDetailCustomer(@PathVariable("customerId") Integer customerId) {
        try {
            Optional<CustomerDTO> customer = customerService.findById(customerId);
            if (customer.isEmpty()) {
                throw new BadRequestException();
            }
            return success(customer.get());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "customer"), ex);
        }
    }

    @Operation(summary = "Create customer")
    @PostMapping("/insert")
    @PreAuthorize("@vldModuleSales.insertCustomer(true)")
    public AppResponse<String> createCustomer(@RequestBody CustomerDTO customer) {
        try {
            if (customer == null) {
                throw new BadRequestException();
            }
            customerService.save(customer);
            return success(MessageUtils.CREATE_SUCCESS);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "customer"), ex);
        }
    }

    @Operation(summary = "Update customer")
    @PutMapping("/update/{customerId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<String> updateCustomer(@RequestBody CustomerDTO customer, @PathVariable("customerId") Integer customerId) {
        try {
            if (customer == null || customerId <= 0 || customerService.findById(customerId).isEmpty()) {
                throw new BadRequestException();
            }
            customerService.update(customer, customerId);
            return success(MessageUtils.UPDATE_SUCCESS);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "customer"), ex);
        }
    }

    @Operation(summary = "Delete customer")
    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("@vldModuleSales.deleteCustomer(true)")
    public AppResponse<String> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        return success(customerService.delete(customerId));
    }
}