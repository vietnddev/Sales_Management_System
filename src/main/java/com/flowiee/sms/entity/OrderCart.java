package com.flowiee.sms.entity;

import com.flowiee.sms.core.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pro_order_cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderCart extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "orderCart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Items> listItems;

	public OrderCart(int id) {
		super.id = id;
	}

	@Override
	public String toString() {
		return "OrderCart [id=" + id + ", getCreatedAt()=" + getCreatedAt() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getLastUpdatedAt()=" + getLastUpdatedAt() + ", getLastUpdatedBy()=" + getLastUpdatedBy() + "]";
	}
}