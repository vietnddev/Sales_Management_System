package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO extends Customer implements Serializable {
    private String phoneDefault;
    private String emailDefault;
    private String addressDefault;
    private BigDecimal orderAvgValue;
    private String customerGroup;

    public static CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setSex(customer.isSex());
        if (customer.getBirthday() != null) {
            dto.setBirthday(customer.getBirthday());
        }
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setCreatedBy(customer.getCreatedBy());
        dto.setListOrder(customer.getListOrder());
        return dto;
    }

    public static List<CustomerDTO> fromCustomers(List<Customer> customers) {
        List<CustomerDTO> listCustomerDTO = new ArrayList<>();
        customers.forEach(customer -> {
            listCustomerDTO.add(fromCustomer(customer));
        });
        return listCustomerDTO;
    }
}