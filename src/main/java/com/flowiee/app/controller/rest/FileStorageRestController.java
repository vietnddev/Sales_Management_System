package com.flowiee.app.controller.rest;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.security.ValidateModuleProduct;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${app.api.prefix}/file")
@Tag(name = "File API", description = "Quản lý file đính kèm và hình ảnh sản phẩm")
public class FileStorageRestController extends BaseController {
    @Autowired private FileStorageService fileService;
    @Autowired private ValidateModuleProduct validateModuleProduct;

    @Operation(summary = "Xóa file", description = "Xóa theo id")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable("id") Integer fileId) {
        try {
            validateModuleProduct.updateImage(true);
            if (fileId <= 0 || fileService.findById(fileId) == null) {
                throw new NotFoundException("Image not found!");
            }
            return com.flowiee.app.model.ApiResponse.ok(fileService.delete(fileId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "file"));
        }
    }
}