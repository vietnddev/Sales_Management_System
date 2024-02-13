package com.flowiee.app.controller.ui;

import com.flowiee.app.entity.Material;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtils;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.service.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(EndPointUtil.STORAGE_MATERIAL)
public class MaterialUIController extends BaseController {
    @Autowired private MaterialService materialService;
    @Autowired private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView viewMaterials() {
        validateModuleStorage.readMaterial(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_MATERIAL);
        modelAndView.addObject("templateImportName", "Name");
        modelAndView.addObject("url_template", EndPointUtil.STORAGE_MATERIAL_TEMPLATE);
        modelAndView.addObject("url_import", EndPointUtil.STORAGE_MATERIAL_IMPORT);
        modelAndView.addObject("url_export", EndPointUtil.STORAGE_MATERIAL_EXPORT);
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    public ModelAndView insert(@ModelAttribute("material") Material material) {
        validateModuleStorage.insertMaterial(true);
        material.setStatus(true);
        materialService.save(material);
        return new ModelAndView("redirect:");
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@ModelAttribute("material") Material material,
                         @PathVariable("id") Integer materialId,
                         HttpServletRequest request) {
        validateModuleStorage.updateMaterial(true);
        if (materialId <= 0 || materialService.findById(materialId) == null) {
            throw new NotFoundException("Material not found!");
        }
        materialService.update(material, materialId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer materialId, HttpServletRequest request) {
        validateModuleStorage.deleteMaterial(true);
        if (materialId <= 0 || materialService.findById(materialId) == null) {
            throw new NotFoundException("Material not found!");
        }
        materialService.delete(materialId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @GetMapping("/template")
    public ResponseEntity<?> exportTemplate() {
        validateModuleStorage.readMaterial(true);
        return null;
    }

    @PostMapping(EndPointUtil.STORAGE_MATERIAL_IMPORT)
    public ModelAndView importData(@RequestParam("file") MultipartFile file) {
        validateModuleStorage.insertMaterial(true);
//        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
//            donViTinhService.importData(file);
//            return "redirect:" + EndPointUtil.DANHMUC_DONVITINH_VIEW;
//        } else {
//            return PagesUtil.PAGE_UNAUTHORIZED;
//        }
        return null;
    }

    @GetMapping(EndPointUtil.STORAGE_MATERIAL_EXPORT)
    public ResponseEntity<?> exportData() {
        validateModuleStorage.readMaterial(true);
//        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
//            byte[] dataExport = donViTinhService.exportData();
//            HttpHeaders header = new HttpHeaders();
//            header.setContentType(new MediaType("application", "force-download"));
//            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx");
//            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
//        }
        return null;
    }
}