package com.flowiee.app.dto;

import com.flowiee.app.entity.OrderDetail;
import com.flowiee.app.entity.ProductVariant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
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
    private Float price;
    private Float priceOriginal;
    private String note;
    private Boolean status;

    public static OrderDetailDTO fromOrderDetail(OrderDetail d) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(d.getId());
        dto.setOrderId(d.getOrder().getId());
        dto.setProductVariantDTO(ProductVariantDTO.fromProductVariant(d.getProductVariant()));
        dto.setQuantity(d.getSoLuong());
        dto.setPrice(d.getPrice());
        dto.setPriceOriginal(d.getPriceOriginal());
        dto.setNote(ObjectUtils.isNotEmpty(d.getGhiChu()) ? d.getGhiChu() : "");
        dto.setStatus(d.isTrangThai());
        return dto;
    }

    public static List<OrderDetailDTO> fromOrderDetails(List<OrderDetail> listDetails) {
        List<OrderDetailDTO> list = new ArrayList<>();
        for (OrderDetail o : listDetails) {
            list.add(OrderDetailDTO.fromOrderDetail(o));
        }
        return list;
    }
}