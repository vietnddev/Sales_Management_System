package com.flowiee.app.dto;

import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderId;
    private String orderCode;
	private Date orderTime;
    private String orderTimeStr;
    private String receiverAddress;
    private String receiverPhone;
	private String receiverEmail;
    private String receiverName;
    private Customer orderBy; //future remove
    private Integer customerId;
    private String customerName;
	private Double amountDiscount;
    private Double totalAmount;
    private Double totalAmountDiscount;
    private Category salesChannel; //future remove
    private Integer salesChannelId;
    private String salesChannelName;
    private String note;
    private Category orderStatus; //future remove
    private Integer orderStatusId;
    private String orderStatusName;
    //private OrderPay orderPay; //future remove
    private Integer orderPayId;
    private String orderPayName;
    private Category payMethod; //future remove
    private Integer payMethodId;
    private String payMethodName;
    private Account cashier; //future remove
    private Integer cashierId;
    private String cashierName;
    private Account createdBy; //future remove
    private Integer createdById;
    private String createdByName;
    private Date createdAt;
    private String qrCode;
    private int totalProduct;
	private String voucherUsedCode;
	private boolean paymentStatus;
	private Date paymentTime;
	private String paymentTimeStr;
	private List<OrderDetail> listOrderDetail;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderDTO [orderId=");
		builder.append(orderId);
		builder.append(", orderCode=");
		builder.append(orderCode);
		builder.append(", orderTime=");
		builder.append(orderTime);
		builder.append(", receiverAddress=");
		builder.append(receiverAddress);
		builder.append(", receiverPhone=");
		builder.append(receiverPhone);
		builder.append(", receiverName=");
		builder.append(receiverName);
		builder.append(", orderBy=");
		builder.append(orderBy);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", customerName=");
		builder.append(customerName);
		builder.append(", totalAmount=");
		builder.append(totalAmount);
		builder.append(", amountDiscount=");
		builder.append(amountDiscount);
		builder.append(", totalAmountAfterDiscount=");
		builder.append(totalAmountDiscount);
		builder.append(", salesChannel=");
		builder.append(salesChannel);
		builder.append(", salesChannelId=");
		builder.append(salesChannelId);
		builder.append(", salesChannelName=");
		builder.append(salesChannelName);
		builder.append(", note=");
		builder.append(note);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", orderStatusId=");
		builder.append(orderStatusId);
		builder.append(", orderStatusName=");
		builder.append(orderStatusName);
		//builder.append(", orderPay=");
		//builder.append(orderPay);
		builder.append(", orderPayId=");
		builder.append(orderPayId);
		builder.append(", orderPayName=");
		builder.append(orderPayName);
		builder.append(", payMethod=");
		builder.append(payMethod);
		builder.append(", payMethodId=");
		builder.append(payMethodId);
		builder.append(", payMethodName=");
		builder.append(payMethodName);
		builder.append(", cashier=");
		builder.append(cashier);
		builder.append(", cashierId=");
		builder.append(cashierId);
		builder.append(", cashierName=");
		builder.append(cashierName);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdByName=");
		builder.append(createdByName);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append("]");
		return builder.toString();
	}
}