package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.service.sales.CartService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/order")
@Tag(name = "Order API", description = "Quản lý giỏ hàng")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemsService cartItemsService;

    @PutMapping("/cart/{cartId}/item/update/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public AppResponse<Items> updateItemsOfCart(@RequestBody Items items,
                                                @PathVariable("cartId") Integer cartId,
                                                @PathVariable("itemId") Integer itemId) {
        try {
            if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
                throw new BadRequestException();
            }
            if (itemId <= 0 || cartItemsService.findById(itemId).isEmpty()) {
                throw new BadRequestException();
            }
            items.setId(itemId);
            items.setOrderCart(cartService.findById(cartId).get());
            if (items.getQuantity() > 0) {
                cartItemsService.save(items);
            } else {
                cartItemsService.delete(items.getId());
            }
            return success(null);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "items"), ex);
        }
    }

    @PostMapping("/cart/{cartId}/item/add")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public AppResponse<List<Items>> addItemsToCart(@RequestBody String[] bienTheSanPhamId, @PathVariable("cartId") Integer cartId) {
        try {
            if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
                throw new BadRequestException();
            }
            List<String> listProductVariantId = Arrays.stream(bienTheSanPhamId).toList();
            for (String productVariantId : Arrays.stream(bienTheSanPhamId).toList()) {
                if (cartService.isItemExistsInCart(cartId, Integer.parseInt(productVariantId))) {
                    Items items = cartItemsService.findItemByCartAndProductVariant(cartId, Integer.parseInt(productVariantId));
                    cartItemsService.increaseItemQtyInCart(items.getId(), items.getQuantity() + 1);
                } else {
                    Items items = new Items();
                    items.setOrderCart(new OrderCart(cartId));
                    items.setProductDetail(new ProductDetail(Integer.parseInt(productVariantId)));
                    items.setQuantity(1);
                    items.setNote("");
                    cartItemsService.save(items);
                }
            }
            return success(null);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "items"), ex);
        }
    }

    @DeleteMapping("/cart/{cartId}/item/delete/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public AppResponse<String> deleteItemsOfCart(@PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        try {
            if (cartService.findById(cartId).isEmpty()) {
                throw new BadRequestException();
            }
            return success(cartItemsService.delete(itemId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "items"), ex);
        }
    }
}