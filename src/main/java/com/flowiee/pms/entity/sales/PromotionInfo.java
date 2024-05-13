package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.model.dto.PromotionInfoDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotion_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PromotionInfo extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "discount_price_max")
    private BigDecimal discountPriceMax;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    public static PromotionInfo fromDTO(PromotionInfoDTO inputDTO) {
        PromotionInfo promotionInfo = new PromotionInfo();
        promotionInfo.setId(inputDTO.getId());
        promotionInfo.setTitle(inputDTO.getTitle());
        promotionInfo.setDescription(inputDTO.getDescription());
        promotionInfo.setDiscountPercent(inputDTO.getDiscountPercent());
        promotionInfo.setDiscountPrice(inputDTO.getDiscountPrice());
        promotionInfo.setDiscountPriceMax(inputDTO.getDiscountPriceMax());
        return promotionInfo;
    }
}