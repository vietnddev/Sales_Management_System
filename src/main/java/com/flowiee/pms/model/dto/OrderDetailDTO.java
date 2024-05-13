package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.utils.converter.ProductVariantConvert;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDetailDTO extends OrderDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer orderId;
    private ProductVariantDTO productVariantDTO;

    public static OrderDetailDTO fromOrderDetail(OrderDetail d) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(d.getId());
        dto.setOrderId(d.getOrder().getId());
        dto.setProductVariantDTO(ProductVariantConvert.entityToDTO(d.getProductDetail()));
        dto.setQuantity(d.getQuantity());
        dto.setPrice(d.getPrice());
        dto.setPriceOriginal(d.getPriceOriginal());
        dto.setNote(ObjectUtils.isNotEmpty(d.getNote()) ? d.getNote() : "");
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