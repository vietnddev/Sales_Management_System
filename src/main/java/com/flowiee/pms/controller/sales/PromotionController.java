package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.sales.PromotionInfo;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.PromotionInfoDTO;
import com.flowiee.pms.service.sales.PromotionService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/promotion")
@Tag(name = "Promotion API", description = "Quản lý promotion, promotion will be deducted from the price of the product")
public class PromotionController extends BaseController {
    @Autowired
    private PromotionService promotionService;
    
    @Operation(summary = "Find all promotions")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readPromotion(true)")
    public AppResponse<List<PromotionInfoDTO>> findPromotions(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                         @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            Page<PromotionInfoDTO> promotionPage = promotionService.findAll(pageSize, pageNum - 1, null, null, null, null);
            return success(promotionPage.getContent(), pageNum, pageSize, promotionPage.getTotalPages(), promotionPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "promotion"), ex);
        }
    }

    @Operation(summary = "Find detail promotion")
    @GetMapping("/{promotionId}")
    @PreAuthorize("@vldModuleSales.readPromotion(true)")
    public AppResponse<PromotionInfo> findDetailPromotion(@PathVariable("promotionId") Integer promotionId) {
        try {
            Optional<PromotionInfoDTO> promotion = promotionService.findById(promotionId);
            if (promotion.isEmpty()) {
                throw new BadRequestException("Promotion not found");
            }
            return success(promotion.get());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "promotion"), ex);
        }
    }

    @Operation(summary = "Create promotion")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleSales.insertPromotion(true)")
    public AppResponse<PromotionInfoDTO> createPromotion(@RequestBody PromotionInfoDTO promotion) {
        try {
            return success(promotionService.save(promotion));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "promotion"), ex);
        }
    }

    @Operation(summary = "Update promotion")
    @PutMapping("/update/{promotionId}")
    @PreAuthorize("@vldModuleSales.updatePromotion(true)")
    public AppResponse<PromotionInfoDTO> updatePromotion(@RequestBody PromotionInfoDTO promotion, @PathVariable("promotionId") Integer promotionId) {
        try {
            return success(promotionService.update(promotion, promotionId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "promotion"), ex);
        }
    }

    @Operation(summary = "Delete promotion")
    @DeleteMapping("/delete/{promotionId}")
    @PreAuthorize("@vldModuleSales.deletePromotion(true)")
    public AppResponse<String> deletePromotion(@PathVariable("promotionId") Integer promotionId) {
        return success(promotionService.delete(promotionId));
    }
}