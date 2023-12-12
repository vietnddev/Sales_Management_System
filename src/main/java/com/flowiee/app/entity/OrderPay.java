package com.flowiee.app.entity;

import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pro_order_pay")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderPay extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id", nullable = false)
    private Order order;

    @Column(name = "ma_phieu", nullable = false)
    private String maPhieu;

    @Column(name = "thoi_gian_thanh_toan", nullable = false)
    private Date thoiGianThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hinh_thuc_thanh_toan", nullable = false)
    private Category hinhThucThanhToan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cashier", nullable = false)
    private Account thuNgan;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "payment_status", nullable = false)
    private Boolean paymentStatus;

    @OneToMany(mappedBy = "orderPay", fetch = FetchType.LAZY)
    private List<OrderHistory> listOrderHistory;

    public OrderPay(int id, boolean paymentStatus) {
        super.id = id;
        this.paymentStatus = paymentStatus;
    }

	@Override
	public String toString() {
		return "OrderPay [id=" + super.id + ", order=" + order + ", maPhieu=" + maPhieu + ", thoiGianThanhToan=" + thoiGianThanhToan
				+ ", hinhThucThanhToan=" + hinhThucThanhToan + ", thuNgan=" + thuNgan + ", ghiChu=" + ghiChu
				+ ", paymentStatus=" + paymentStatus + "]";
	}        
}