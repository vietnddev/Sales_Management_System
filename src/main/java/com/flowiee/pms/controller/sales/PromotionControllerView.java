package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.model.dto.PromotionInfoDTO;
import com.flowiee.pms.service.sales.PromotionService;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/promotion")
public class PromotionControllerView extends BaseController {
    private final PromotionService promotionService;

    public PromotionControllerView(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleSales.readPromotion(true)")
    public ModelAndView findAll() {
        return baseView(new ModelAndView(PagesUtils.PRO_PROMOTION));
    }

    @GetMapping(value = "/{promotionId}")
    @PreAuthorize("@vldModuleSales.readPromotion(true)")
    public ModelAndView findDetail(@PathVariable("promotionId") Integer promotionId) {
        Optional<PromotionInfoDTO> promotion = promotionService.findById(promotionId);
        if (promotion.isEmpty()) {
            throw new NotFoundException("Promotion not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_PROMOTION_DETAIL);
        modelAndView.addObject("promotion", promotion.get());
        return baseView(modelAndView);
    }
}