package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.payload.CartItemsReq;
import com.flowiee.pms.service.sales.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("${app.api.prefix}/sls/cart")
@Tag(name = "Cart API", description = "Quản lý giỏ hàng")
@RequiredArgsConstructor
public class CartController extends BaseController {
    private final CartService cartService;

    @PostMapping("/add-items")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public AppResponse<String> addItemsToCart(@RequestBody CartItemsReq request) {
        OrderCart cart = cartService.findById(request.getCartId(), true);
        //mvCartService.addItemsToCart(cart.getId(), bienTheSanPhamId);
        System.out.println(request);
        return success(null, "Success!");
    }
}