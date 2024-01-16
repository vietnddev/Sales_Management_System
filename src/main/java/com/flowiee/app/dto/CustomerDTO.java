package com.flowiee.app.dto;

import com.flowiee.app.entity.Customer;
import com.flowiee.app.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO implements Serializable {
    private Integer id;
    private String name;
    private String sex;
    private String birthday;
    private String phoneDefault;
    private String emailDefault;
    private String addressDefault;
    private Date createdAt;
    private Integer createdBy;

    public static CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getTenKhachHang());
        if (customer.isGioiTinh()) {
            dto.setSex("Nam");
        } else {
            dto.setSex("Nữ");
        }
        if (customer.getBirthday() != null) {
            dto.setBirthday(DateUtils.convertDateToString("yyyy-MM-dd HH:mm:ss.S", "dd/MM/yyyy", customer.getBirthday()));
        }
//      dto.setPhoneDefault(customer.getPhoneDefault());
//      dto.setEmailDefault(customer.getEmailDefault());
//      dto.setAddressDefault(customer.getAddressDefault());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setCreatedBy(customer.getCreatedBy());
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