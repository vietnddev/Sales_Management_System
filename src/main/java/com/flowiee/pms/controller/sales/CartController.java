package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.service.sales.CartService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/don-hang")
@Tag(name = "Order API", description = "Quản lý giỏ hàng")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemsService cartItemsService;

    @PostMapping("/ban-hang/cart/item/add")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView addItemsToCart(@RequestParam("cartId") Integer cartId, @RequestParam("bienTheSanPhamId") String[] bienTheSanPhamId) {
        if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
            throw new NotFoundException("Cart not found!");
        }
        cartService.addItemsToCart(cartId, bienTheSanPhamId);
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/update/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView updateItemsOfCart(@RequestParam("cartId") Integer cartId,
                                          @ModelAttribute("items") Items items,
                                          @PathVariable("itemId") Integer itemId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new NotFoundException("Cart not found!");
        }
        cartService.updateItemsOfCart(items, itemId);
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/delete/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView deleteItemsOfCart(@RequestParam("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new BadRequestException("Sản phẩm cần xóa trong giỏ hàng không tồn tại! cartId=" + cartId + ", itemId=" + itemId);
        }
        cartItemsService.delete(itemId);
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/{cartId}/reset")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView resetCart(@PathVariable("cartId") Integer cartId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new BadRequestException("Cart not found! cartId=" + cartId);
        }
        cartService.resetCart(cartId);
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

//    @PutMapping("/cart/{cartId}/item/update/{itemId}")
//    @PreAuthorize("@vldModuleSales.insertOrder(true)")
//    public AppResponse<Items> updateItemsOfCart(@RequestBody Items items,
//                                                @PathVariable("cartId") Integer cartId,
//                                                @PathVariable("itemId") Integer itemId) {
//        try {
//            if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
//                throw new BadRequestException();
//            }
//            if (itemId <= 0 || cartItemsService.findById(itemId).isEmpty()) {
//                throw new BadRequestException();
//            }
//            items.setId(itemId);
//            items.setOrderCart(cartService.findById(cartId).get());
//            if (items.getQuantity() > 0) {
//                cartItemsService.save(items);
//            } else {
//                cartItemsService.delete(items.getId());
//            }
//            return success(null);
//        } catch (RuntimeException ex) {
//            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "items"), ex);
//        }
//    }

//    @PostMapping("/cart/{cartId}/item/add")
//    @PreAuthorize("@vldModuleSales.insertOrder(true)")
//    public AppResponse<List<Items>> addItemsToCart(@RequestBody String[] bienTheSanPhamId, @PathVariable("cartId") Integer cartId) {
//        try {
//            if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
//                throw new BadRequestException();
//            }
//            List<String> listProductVariantId = Arrays.stream(bienTheSanPhamId).toList();
//            for (String productVariantId : Arrays.stream(bienTheSanPhamId).toList()) {
//                if (cartService.isItemExistsInCart(cartId, Integer.parseInt(productVariantId))) {
//                    Items items = cartItemsService.findItemByCartAndProductVariant(cartId, Integer.parseInt(productVariantId));
//                    cartItemsService.increaseItemQtyInCart(items.getId(), items.getQuantity() + 1);
//                } else {
//                    Items items = new Items();
//                    items.setOrderCart(new OrderCart(cartId));
//                    items.setProductDetail(new ProductDetail(Integer.parseInt(productVariantId)));
//                    items.setQuantity(1);
//                    items.setNote("");
//                    cartItemsService.save(items);
//                }
//            }
//            return success(null);
//        } catch (RuntimeException ex) {
//            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "items"), ex);
//        }
//    }

//    @DeleteMapping("/cart/{cartId}/item/delete/{itemId}")
//    @PreAuthorize("@vldModuleSales.insertOrder(true)")
//    public AppResponse<String> deleteItemsOfCart(@PathVariable("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
//        try {
//            if (cartService.findById(cartId).isEmpty()) {
//                throw new BadRequestException();
//            }
//            return success(cartItemsService.delete(itemId));
//        } catch (RuntimeException ex) {
//            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "items"), ex);
//        }
//    }
}