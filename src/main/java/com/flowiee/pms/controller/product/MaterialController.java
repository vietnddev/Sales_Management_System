package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.MaterialDTO;
import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.service.product.MaterialService;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.converter.MaterialConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/storage/material")
@Tag(name = "Material API", description = "Quản lý nguyên vật liệu")
public class MaterialController extends BaseController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @Operation(summary = "Find all nguyên vật liệu")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleProduct.readMaterial(true)")
    public AppResponse<List<Material>> findAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (pageSize == null) pageSize = -1;
            if (pageNum == null) pageNum = 1;
            Page<Material> materials = materialService.findAll(pageSize, pageNum - 1, null, null, null, null, null, null);
            return success(materials.getContent(), pageNum, pageSize, materials.getTotalPages(), materials.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "material"), ex);
        }
    }

    @Operation(summary = "Create new material")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleProduct.insertMaterial(true)")
    public AppResponse<MaterialDTO> insert(@RequestBody MaterialDTO materialDTO) {
        try {
            return success(MaterialConvert.convertToDTO(materialService.save(Material.fromMaterialDTO(materialDTO))));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "material"), ex);
        }
    }
}