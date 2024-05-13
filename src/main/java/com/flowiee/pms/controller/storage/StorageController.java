package com.flowiee.pms.controller.storage;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.StorageItems;
import com.flowiee.pms.model.dto.StorageDTO;
import com.flowiee.pms.service.storage.StorageExportService;
import com.flowiee.pms.service.storage.StorageService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/storage")
@Tag(name = "Storage API", description = "Storage management")
public class StorageController extends BaseController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private StorageExportService storageExportService;

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
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "storage"), ex);
        }
    }

    @Operation(summary = "Find detail storage")
    @GetMapping("/{storageId}")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public AppResponse<StorageDTO> findDetailStorage(@PathVariable("storageId") Integer storageId) {
        try {
            Optional<StorageDTO> storage = storageService.findById(storageId);
            if (storage.isEmpty()) {
                throw new BadRequestException("Storage not found");
            }
            return success(storage.get());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "storage"), ex);
        }
    }

    @Operation(summary = "Create storage")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleStorage.insertStorage(true)")
    public AppResponse<StorageDTO> createStorage(@RequestBody StorageDTO storage) {
        try {
            return success(storageService.save(storage));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "storage"), ex);
        }
    }

    @Operation(summary = "Update storage")
    @PutMapping("/update/{storageId}")
    @PreAuthorize("@vldModuleStorage.updateStorage(true)")
    public AppResponse<StorageDTO> updateStorage(@RequestBody StorageDTO storage, @PathVariable("storageId") Integer storageId) {
        try {
            return success(storageService.update(storage, storageId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "storage"), ex);
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
            System.out.println("searchText " + searchText);
            Optional<StorageDTO> storage = storageService.findById(storageId);
            if (storage.isEmpty()) {
                throw new BadRequestException("Storage not found");
            }
            Page<StorageItems> storageItemsPage = storageService.findStorageItems(pageSize, pageNum -1, storageId, searchText);
            return success(storageItemsPage.getContent(), pageNum, pageSize, storageItemsPage.getTotalPages(), storageItemsPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "storage"), ex);
        }
    }

    @Operation(summary = "Export storage information")
    @GetMapping("/export")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public ResponseEntity<?> exportData(@RequestParam("storageId") Integer storageId) {
        return storageExportService.exportToExcel(storageId);
    }
}