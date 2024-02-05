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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/storage/material")
@Tag(name = "Material API", description = "Quản lý nguyên vật liệu")
public class MaterialController extends BaseController {
    @Autowired private MaterialService materialService;

    @Operation(summary = "Find all nguyên vật liệu")
    @GetMapping("/all")
    public ApiResponse<List<Material>> findAll() {
        try {
            if (!super.validateModuleStorage.readMaterial(true)) {
                return null;
            }
            return ApiResponse.ok(materialService.findAll());
        } catch (Exception ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "material"));
        }
    }
}