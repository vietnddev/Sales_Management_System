package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PEND("Pending", ""),                  // Đang chờ xử lý
    CONF("Confirmed", ""),                // Đã xác nhận
    PROC("Processing", ""),               // Đang xử lý
    SHIP("Shipped", ""),                  // Đã giao cho đơn vị vận chuyển
    DLVD("Delivered", ""),                // Giao thành công
    CNCL("Cancelled", ""),                // Đã hủy
    RTND("Returned", ""),                 // Đã trả hàng
    //FAIL("Failed", ""),                   // Lỗi
    //HOLD("On Hold", ""),                  // Tạm dừng
    //RFND("Refunded", ""),                 // Hoàn tiền
    PART_DLVD("Partially Delivered", ""), // Giao một phần
    //EXPD("Expired", ""),                  // Hết hạn
    AWTP("Awaiting Pickup", ""),          // Chờ lấy hàng
    DSPT("Disputed", ""),                 // Đang tranh chấp
    ALL("All", "");                       // Tất cả trạng thái

    private String name;
    private String description;

    OrderStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static OrderStatus get(String pOrderStatus) {
        if (pOrderStatus == null || pOrderStatus.isBlank()) {
            return null;
        }
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.name.equalsIgnoreCase(pOrderStatus.trim())) {
                return orderStatus;
            }
        }
        return null;
    }
}