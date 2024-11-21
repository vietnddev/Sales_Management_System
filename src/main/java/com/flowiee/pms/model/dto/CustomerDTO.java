package com.flowiee.pms.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.product.ProductReview;
import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.entity.sales.LoyaltyTransaction;
import com.flowiee.pms.entity.sales.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDTO extends Customer implements Serializable {
	@Serial
	static final long serialVersionUID = 1L;
	
	String phoneDefault;
    String emailDefault;
    String addressDefault;
    BigDecimal orderAvgValue;
    String customerGroup;
    String profilePictureUrl;
    LocalDate lastPurchaseDate;
    BigDecimal totalSpent;

    public static CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCustomerName(customer.getCustomerName());
        if (customer.getDateOfBirth() != null)
            dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setSex(customer.isSex());
        dto.setMaritalStatus(customer.getMaritalStatus());
        dto.setReferralSource(customer.getReferralSource());
        dto.setIsBlackList(customer.getIsBlackList());
        dto.setBlackListReason(customer.getBlackListReason());
        dto.setBonusPoints(customer.getBonusPoints());
        dto.setHasOutstandingBalance(customer.getHasOutstandingBalance());
        dto.setOutstandingBalanceAmount(customer.getOutstandingBalanceAmount());
        dto.setListOrder(customer.getListOrder());
        dto.setListProductReviews(customer.getListProductReviews());
        dto.setListCustomerContact(customer.getListCustomerContact());
        dto.setLoyaltyTransactionList(customer.getLoyaltyTransactionList());
        mappingBaseAudit(dto, customer);

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