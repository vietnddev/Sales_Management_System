package com.flowiee.pms.controller.storage;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.EximModel;
import com.flowiee.pms.model.StorageItems;
import com.flowiee.pms.model.dto.StorageDTO;
import com.flowiee.pms.service.ExportService;
import com.flowiee.pms.service.storage.StorageService;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.TemplateExport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/storage")
@Tag(name = "Storage API", description = "Storage management")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StorageController extends BaseController {
    StorageService storageService;
    ExportService  exportService;

    public StorageController(StorageService storageService, @Qualifier("storageExportServiceImpl") ExportService exportService) {
        this.storageService = storageService;
        this.exportService = exportService;
    }

    @Operation(summary = "Find all storages")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public AppResponse<List<StorageDTO>> findStorages(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (pageSize == null || pageNum == null) {
                return success(storageService.findAll());
            }
            Page<StorageDTO> storagePage = storageService.findAll(pageSize, pageNum - 1);
            return success(storagePage.getContent(), pageNum, pageSize, storagePage.getTotalPages(), storagePage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "storage"), ex);
        }
    }

    @Operation(summary = "Find detail storage")
    @GetMapping("/{storageId}")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public AppResponse<StorageDTO> findDetailStorage(@PathVariable("storageId") Integer storageId) {
        Optional<StorageDTO> storage = storageService.findById(storageId);
        if (storage.isEmpty()) {
            throw new ResourceNotFoundException("Storage not found");
        }
        return success(storage.get());
    }

    @Operation(summary = "Create storage")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleStorage.insertStorage(true)")
    public AppResponse<StorageDTO> createStorage(@RequestBody StorageDTO storage) {
        try {
            return success(storageService.save(storage));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "storage"), ex);
        }
    }

    @Operation(summary = "Update storage")
    @PutMapping("/update/{storageId}")
    @PreAuthorize("@vldModuleStorage.updateStorage(true)")
    public AppResponse<StorageDTO> updateStorage(@RequestBody StorageDTO storage, @PathVariable("storageId") Integer storageId) {
        try {
            return success(storageService.update(storage, storageId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "storage"), ex);
        }
    }

    @Operation(summary = "Delete storage")
    @DeleteMapping("/delete/{storageId}")
    @PreAuthorize("@vldModuleStorage.deleteStorage(true)")
    public AppResponse<String> deleteStorage(@PathVariable("storageId") Integer storageId) {
        return success(storageService.delete(storageId));
    }

    @Operation(summary = "Find detail storage")
    @GetMapping("/{storageId}/items")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public AppResponse<List<StorageItems>> findStorageItems(@PathVariable("storageId") Integer storageId,
                                                            @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                            @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                            @RequestParam(value = "searchText", required = false) String searchText) {
        try {
            Page<StorageItems> storageItemsPage = storageService.findStorageItems(pageSize, pageNum -1, storageId, searchText);
            return success(storageItemsPage.getContent(), pageNum, pageSize, storageItemsPage.getTotalPages(), storageItemsPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "storage"), ex);
        }
    }

    @Operation(summary = "Export storage information")
    @GetMapping("/export")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public ResponseEntity<InputStreamResource> exportData(@RequestParam("storageId") Integer storageId) {
        EximModel model = exportService.exportToExcel(TemplateExport.EX_STORAGE_ITEMS, new Storage(storageId), false);
        return ResponseEntity.ok().headers(model.getHttpHeaders()).body(model.getContent());
    }
}