package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.PurchaseHistory;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.MessageCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerController extends BaseController {
    CustomerService customerService;

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
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "customer"), ex);
        }
    }

    @Operation(summary = "Find detail customer")
    @GetMapping("/{customerId}")
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public AppResponse<CustomerDTO> findDetailCustomer(@PathVariable("customerId") Integer customerId) {
        Optional<CustomerDTO> customer = customerService.findById(customerId);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "customer"));
        }
        return success(customer.get());
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
            return success(MessageCode.CREATE_SUCCESS.getDescription());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "customer"), ex);
        }
    }

    @Operation(summary = "Update customer")
    @PutMapping("/update/{customerId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customer, @PathVariable("customerId") Integer customerId) {
        return success(customerService.update(customer, customerId));
    }

    @Operation(summary = "Delete customer")
    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("@vldModuleSales.deleteCustomer(true)")
    public AppResponse<String> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        return success(customerService.delete(customerId));
    }

    @Operation(summary = "Find the number of purchase of customer per month")
    @GetMapping("/purchase-history/{customerId}")
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public AppResponse<List<PurchaseHistory>> findPurchaseHistory(@PathVariable("customerId") Integer customerId, @RequestParam(value = "year", required = false) Integer year) {
        if (customerService.findById(customerId).isEmpty()) {
            throw new BadRequestException("Customer not found");
        }
        return success(customerService.findPurchaseHistory(customerId, year, null));
    }
}