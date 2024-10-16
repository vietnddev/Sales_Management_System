package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.ProductCombo;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.service.product.ProductComboService;
import com.flowiee.pms.utils.constants.Pages;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/product/combo")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductComboControllerView extends BaseController {
    ProductComboService mvProductComboService;

    @GetMapping
    @PreAuthorize("@vldModuleProduct.readCombo(true)")
    public ModelAndView findAll() {
        return baseView(new ModelAndView(Pages.PRO_COMBO.getTemplate()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleProduct.readCombo(true)")
    public ModelAndView findDetail(@PathVariable("id") Long productComboId) {
        Optional<ProductCombo> productCombo = mvProductComboService.findById(productComboId);
        if (productCombo.isEmpty()) {
            throw new ResourceNotFoundException("Combo not found!");
        }
        ModelAndView modelAndView = new ModelAndView(Pages.PRO_COMBO_DETAIL.getTemplate());
        modelAndView.addObject("productComboDetail", productCombo.get());
        return baseView(modelAndView);
    }
}