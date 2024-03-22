package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderHistory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    private OrderDetail orderDetail;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "field_name", nullable = false)
    private String field;

    @Lob
    @Column(name = "old_value", nullable = false, length = 9999, columnDefinition = "CLOB")
    private String oldValue;

    @Lob
    @Column(name = "new_value", nullable = false, length = 9999, columnDefinition = "CLOB")
    private String newValue;

    public OrderHistory(Integer orderId, Integer orderDetailId, String title, String field, String oldValue, String newValue) {
        this.order = new Order(orderId);
        if (orderDetailId != null) {
            this.orderDetail = new OrderDetail(orderDetailId);
        }
        this.title = title;
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

	@Override
	public String toString() {
		return "OrderHistory [id=" + super.id + ", order=" + order + ", orderDetail=" + orderDetail + ", title="
				+ title + ", fieldName=" + field + ", oldValue=" + oldValue + ", newValue=" + newValue + "]";
	}       
}