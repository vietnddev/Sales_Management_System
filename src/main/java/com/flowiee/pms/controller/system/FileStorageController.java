package com.flowiee.pms.controller.system;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.ApiResponse;
import com.flowiee.pms.service.system.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.api.prefix}/file")
@Tag(name = "File API", description = "Quản lý file đính kèm và hình ảnh sản phẩm")
public class FileStorageController extends BaseController {
    private final FileStorageService fileService;

    @Autowired
    public FileStorageController(FileStorageService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Xóa file", description = "Xóa theo id")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@vldModuleProduct.updateImage(true)")
    public ApiResponse<String> delete(@PathVariable("id") Integer fileId) {
        return ApiResponse.ok(fileService.delete(fileId));
    }
}