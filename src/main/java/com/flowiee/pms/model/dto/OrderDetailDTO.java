package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.OrderDetail;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDetailDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer Id;
    private Integer orderId;
    private ProductVariantDTO productVariantDTO;
    private Integer quantity;
    private BigDecimal unitPrice; //price
    private BigDecimal unitPriceOriginal; //priceOriginal
    private String note;
    private Boolean status;

    public static OrderDetailDTO fromOrderDetail(OrderDetail d) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(d.getId());
        dto.setOrderId(d.getOrder().getId());
        dto.setProductVariantDTO(ProductVariantDTO.fromProductVariant(d.getProductDetail()));
        dto.setQuantity(d.getQuantity());
        dto.setUnitPrice(d.getPrice());
        dto.setUnitPriceOriginal(d.getPriceOriginal());
        dto.setNote(ObjectUtils.isNotEmpty(d.getGhiChu()) ? d.getGhiChu() : "");
        dto.setStatus(d.isStatus());
        return dto;
    }

    public static List<OrderDetailDTO> fromOrderDetails(List<OrderDetail> listDetails) {
        List<OrderDetailDTO> list = new ArrayList<>();
        if (listDetails != null && !listDetails.isEmpty()) {
            for (OrderDetail o : listDetails) {
                list.add(OrderDetailDTO.fromOrderDetail(o));
            }
        }
        return list;
    }
}