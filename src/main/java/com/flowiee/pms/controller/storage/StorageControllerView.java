package com.flowiee.pms.controller.storage;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.dto.StorageDTO;
import com.flowiee.pms.service.storage.StorageService;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/storage")
public class StorageControllerView extends BaseController {
    private final StorageService storageService;

    public StorageControllerView(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public ModelAndView findAll() {
        return baseView(new ModelAndView(PagesUtils.STG_STORAGE));
    }

    @GetMapping(value = "/{storageId}")
    @PreAuthorize("@vldModuleStorage.readStorage(true)")
    public ModelAndView findDetail(@PathVariable("storageId") Integer storageId) {
        Optional<StorageDTO> storage = storageService.findById(storageId);
        if (storage.isEmpty()) {
            throw new ResourceNotFoundException("Storage not found!");
        }
        System.out.println("storageId: " + storageId);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_STORAGE_DETAIL);
        modelAndView.addObject("storageId", storage.get().getId());
        modelAndView.addObject("storage", storage.get());
        return baseView(modelAndView);
    }
}