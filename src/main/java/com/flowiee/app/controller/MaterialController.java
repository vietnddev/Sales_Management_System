package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Material;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.MaterialService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/storage/material")
@Tag(name = "Material API", description = "Quản lý nguyên vật liệu")
public class MaterialController extends BaseController {
    @Autowired private MaterialService materialService;

    @Operation(summary = "Find all nguyên vật liệu")
    @GetMapping("/all")
    public ApiResponse<List<Material>> findAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (!super.validateModuleStorage.readMaterial(true)) {
                return null;
            }
            if (pageSize != null && pageNum != null) {
                Page<Material> materials = materialService.findAll(pageSize, pageNum - 1);
                return ApiResponse.ok(materials.getContent(), pageNum, pageSize, materials.getTotalPages(), materials.getTotalElements());
            } else {
                Page<Material> materials = materialService.findAll(null, null);
                return ApiResponse.ok(materials.getContent(), 1, 0, materials.getTotalPages(), materials.getTotalElements());
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "material"));
        }
    }
}