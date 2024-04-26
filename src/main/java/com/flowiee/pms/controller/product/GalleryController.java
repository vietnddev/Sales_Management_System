package com.flowiee.pms.controller.product;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.product.ProductImageService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Gallery API", description = "Gallery management")
public class GalleryController extends BaseController {
    @Autowired
    private ProductImageService productImageService;

    @Operation(summary = "Find images of all products")
    @GetMapping("/images/all")
    @PreAuthorize("@vldModuleProduct.readGallery(true)")
    public AppResponse<List<FileStorage>> viewGallery() {
        try {
            return success(productImageService.getImageOfProduct(null));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "gallery"), ex);
        }
    }
}