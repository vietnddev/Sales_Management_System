package com.flowiee.app.controller.rest;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.CartService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.utils.DateUtils;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/order")
@Tag(name = "Order API", description = "Quản lý đơn hàng")
public class OrderRestController extends BaseController {
    @Autowired private OrderService orderService;
    @Autowired private CartService cartService;

    @Operation(summary = "Find all orders")
    @GetMapping("/all")
    public ApiResponse<List<OrderDTO>> findAllOrders() {
        try {
            if (!super.validateModuleProduct.readOrder(true)) {
                return null;
            }
            return ApiResponse.ok(orderService.findAllOrder());
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "order"));
        }
    }

    @Operation(summary = "Find detail order")
    @GetMapping("/{orderId}")
    public ApiResponse<OrderDTO> findOrderDetail(@PathVariable("orderId") Integer orderId) {
        if (!super.validateModuleProduct.readOrder(true)) {
            return null;
        }
        try {
            if (orderId <= 0 || orderService.findOrderById(orderId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(orderService.findOrderById(orderId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "order"));
        }
    }

    @Operation(summary = "Create new order")
    @PostMapping("/insert")
    public ApiResponse<String> createOrder(@RequestBody OrderDTO orderRequest) {
        try {
            return ApiResponse.ok(orderService.saveOrder(orderRequest));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "order"));
        }
    }

    @PutMapping("/update/{orderId}")
    public ApiResponse<String> update(@RequestBody Order order, @PathVariable("orderId") Integer orderId) {
        if (!super.validateModuleProduct.updateOrder(true)) {
            return null;
        }
        try {
            if (orderId <= 0 || order == null || orderService.findOrderById(orderId) == null) {
                throw new BadRequestException();
            }
            orderService.updateOrder(order, orderId);
            return ApiResponse.ok(MessageUtils.UPDATE_SUCCESS);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "order"));
        }
    }

    @DeleteMapping("/delete/{orderId}")
    public ApiResponse<String> deleteOrder(@PathVariable("orderId") Integer orderId) {
        if (!super.validateModuleProduct.deleteOrder(true)) {
            return null;
        }
        try {
            //Check them trang thai
            return ApiResponse.ok(orderService.deleteOrder(orderId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "order"));
        }
    }

    @PostMapping("/cart/{cartId}/item/add")
    public ApiResponse<List<Items>> addItemsToCart(@RequestBody String[] bienTheSanPhamId, @PathVariable("cartId") Integer cartId) {
        if (!super.validateModuleProduct.insertOrder(true)) {
            return null;
        }
        try {
            if (cartId <= 0 || cartService.findCartById(cartId) == null) {
                throw new BadRequestException();
            }
            List<String> listProductVariantId = Arrays.stream(bienTheSanPhamId).toList();
            for (String productVariantId : listProductVariantId) {
                if (cartService.isItemExistsInCart(cartId, Integer.parseInt(productVariantId))) {
                    Items items = cartService.findItemByCartAndProductVariant(cartId, Integer.parseInt(productVariantId));
                    cartService.increaseItemQtyInCart(items.getId(), items.getSoLuong() + 1);
                } else {
                    Items items = new Items();
                    items.setOrderCart(new OrderCart(cartId));
                    items.setProductVariant(new ProductVariant(Integer.parseInt(productVariantId)));
                    items.setSoLuong(1);
                    items.setGhiChu("");
                    cartService.saveItem(items);
                }
            }
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "items"));
        }
    }

    @PutMapping("/cart/{cartId}/item/update/{itemId}")
    public ApiResponse<Items> updateItemsOfCart(@RequestBody Items items,
                                          @PathVariable("cartId") Integer cartId,
                                          @PathVariable("itemId") Integer itemId) {
        if (!super.validateModuleProduct.insertOrder(true)) {
            return null;
        }
        try {
            if (cartId <= 0 || cartService.findCartById(cartId) == null) {
                throw new BadRequestException();
            }
            if (itemId <= 0 || cartService.findItemById(itemId) == null) {
                throw new BadRequestException();
            }
            items.setId(itemId);
            items.setOrderCart(cartService.findCartById(cartId));
            if (items.getSoLuong() > 0) {
                cartService.saveItem(items);
            } else {
                cartService.deleteItem(items.getId());
            }
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "items"));
        }
    }

    @DeleteMapping("/cart/{cartId}/item/delete/{itemId}")
    public ApiResponse<String> deleteItemsOfCart(@PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        if (!super.validateModuleProduct.insertOrder(true)) {
            return null;
        }
        try {
            if (cartService.findCartById(cartId) == null || cartService.findItemById(itemId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(cartService.deleteItem(itemId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "items"));
        }
    }

    @PostMapping("/cart/add-voucher/{code}")
    public ApiResponse<VoucherInfo> addVoucherToCart(@PathVariable("code") String voucherCode) {
        if (!super.validateModuleProduct.readVoucher(true)) {
            return null;
        }
        try {
//            ModelAndView modelAndView = new ModelAndView("redirect:/don-hang/ban-hang");
//            modelAndView.addObject("ticket_code", code);
//            modelAndView.addObject("ticket_status", voucherTicketService.checkTicketToUse(code));
//            modelAndView.addObject("ticket_info", voucherTicketService.findByCode(code));
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "add voucher to cart"));
        }
    }

    @PutMapping("/thanh-toan/{orderId}")
    public ApiResponse<String> doPayOrder(@RequestBody String[] payInfo, @PathVariable("orderId") Integer orderId) {
        if (!super.validateModuleProduct.updateOrder(true)) {
            return null;
        }
        try {
            if (orderId == null || orderId <= 0 || orderService.findOrderById(orderId) == null) {
                throw new BadRequestException();
            }
            Date paymentTime = DateUtils.convertStringToDate(null, null, payInfo[0]);
            int paymentMethod = Integer.parseInt(payInfo[1]);
            String note = payInfo[2];
            if (paymentTime == null) {
                paymentTime = new Date();
            }
            if (paymentMethod <= 0) {
                throw new BadRequestException("Hình thức thanh toán không hợp lệ!");
            }
            return ApiResponse.ok(orderService.doPay(orderId, paymentTime, paymentMethod, note));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "pay order"));
        }
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportOrders() {
        if (!super.validateModuleProduct.readOrder(true)) {
            return null;
        }
        try {
            return null;
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "export order"));
        }
    }

    @GetMapping("/scan/QR-Code/{code}")
    public ModelAndView findOrderInfoByQRCode(@PathVariable("code") String code) {
        try {
            //Xử lý code thành id
            return new ModelAndView().addObject("orderInfo", orderService.findOrderById(null));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "scan order"));
        }
    }
}