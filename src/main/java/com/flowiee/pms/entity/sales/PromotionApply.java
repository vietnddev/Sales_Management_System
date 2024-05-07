package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

@Builder
@Entity
@Table(name = "promotion_apply")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PromotionApply extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "promotion_id", nullable = false)
    private Integer promotionId;

	@Override
	public String toString() {
		return "PromotionApply [id=" + super.id + ", productId=" + productId + ", voucherId=" + promotionId + "]";
	}
}