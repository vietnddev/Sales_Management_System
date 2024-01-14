package com.flowiee.app.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer cartId;
    private Integer cashierId;
    private Integer customerId;
    private String note;
    private Integer orderStatusId;
    private Date orderTime;
    private Integer paymentMethodId;
    private Integer salesChannelId;
}