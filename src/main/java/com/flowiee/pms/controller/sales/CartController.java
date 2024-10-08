package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.CartItemModel;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.CartItemsService;
import com.flowiee.pms.service.sales.CartService;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.Pages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "Order API", description = "Quản lý giỏ hàng")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartController extends BaseController {
    CartService           cartService;
    AccountService        accountService;
    CategoryService       categoryService;
    CartItemsService      cartItemsService;
    ProductVariantService productVariantService;

    @GetMapping("/ban-hang")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView showPageBanHang() {
        ModelAndView modelAndView = new ModelAndView(Pages.PRO_ORDER_SELL.getTemplate());
        List<OrderCart> orderCartCurrent = cartService.findCartByAccountId(CommonUtils.getUserPrincipal().getId());
        if (orderCartCurrent.isEmpty()) {
            OrderCart orderCart = new OrderCart();
            orderCart.setCreatedBy(CommonUtils.getUserPrincipal().getId());
            cartService.save(orderCart);
        }

        List<OrderCart> listOrderCart = cartService.findCartByAccountId(CommonUtils.getUserPrincipal().getId());
        modelAndView.addObject("listCart", listOrderCart);
        modelAndView.addObject("listAccount", accountService.findAll());
        modelAndView.addObject("listSalesChannel", categoryService.findSalesChannels());
        modelAndView.addObject("listPaymentMethod", categoryService.findPaymentMethods());
        modelAndView.addObject("listOrderStatus", categoryService.findOrderStatus(null));
        modelAndView.addObject("listProductVariant", productVariantService.findAll(-1, -1, null, null, null, null, null, true).getContent());
        modelAndView.addObject("listItemsForSales", cartItemsService.findAllItemsForSales());

        double totalAmountWithoutDiscount = cartService.calTotalAmountWithoutDiscount(listOrderCart.get(0).getId());
        double amountDiscount = 0;
        double totalAmountDiscount = totalAmountWithoutDiscount - amountDiscount;
        modelAndView.addObject("totalAmountWithoutDiscount", totalAmountWithoutDiscount);
        //modelAndView.addObject("amountDiscount", amountDiscount);
        modelAndView.addObject("totalAmountDiscount", totalAmountDiscount);
        return baseView(modelAndView);
    }

    @Operation(summary = "Get all items available for sales")
    @GetMapping("/cart/product/available-sales")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public AppResponse<List<CartItemModel>> getAllItemsForSales() {
        return success(cartItemsService.findAllItemsForSales());
    }

    @PostMapping("/ban-hang/cart/item/add")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView addItemsToCart(@RequestParam("cartId") Integer cartId, @RequestParam("bienTheSanPhamId") String[] bienTheSanPhamId) {
        if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
            throw new ResourceNotFoundException("Cart not found!");
        }
        cartService.addItemsToCart(cartId, bienTheSanPhamId);
        return new ModelAndView("redirect:/order/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/update/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView updateItemsOfCart(@RequestParam("cartId") Integer cartId,
                                          @ModelAttribute("items") Items items,
                                          @PathVariable("itemId") Integer itemId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new ResourceNotFoundException("Cart not found!");
        }
        cartService.updateItemsOfCart(items, itemId);
        return new ModelAndView("redirect:/order/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/delete/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView deleteItemsOfCart(@RequestParam("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new BadRequestException("Sản phẩm cần xóa trong giỏ hàng không tồn tại! cartId=" + cartId + ", itemId=" + itemId);
        }
        cartItemsService.delete(itemId);
        return new ModelAndView("redirect:/order/ban-hang");
    }

    @PostMapping("/ban-hang/cart/{cartId}/reset")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView resetCart(@PathVariable("cartId") Integer cartId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new BadRequestException("Cart not found! cartId=" + cartId);
        }
        cartService.resetCart(cartId);
        return new ModelAndView("redirect:/order/ban-hang");
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