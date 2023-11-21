package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pro_order_history")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_pay_id")
    private OrderPay orderPay;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Lob
    @Column(name = "old_value", nullable = false, length = 9999, columnDefinition = "TEXT")
    private String oldValue;

    @Lob
    @Column(name = "new_value", nullable = false, length = 9999, columnDefinition = "TEXT")
    private String newValue;
}