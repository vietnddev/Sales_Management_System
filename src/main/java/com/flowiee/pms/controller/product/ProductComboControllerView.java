package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/product/combo")
public class ProductComboControllerView extends BaseController {
    @GetMapping
    @PreAuthorize("@vldModuleProduct.readCombo(true)")
    public ModelAndView findAll() {
        return baseView(new ModelAndView(PagesUtils.PRO_COMBO));
    }
}