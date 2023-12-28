package com.flowiee.app.controller;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.entity.TicketImportGoods;
import com.flowiee.app.service.SupplierService;
import com.flowiee.app.service.TicketImportGoodsService;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.service.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(EndPointUtil.STORAGE_MATERIAL)
public class MaterialController extends BaseController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TicketImportGoodsService ticketImportService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView getAll(@Nullable @RequestParam("ticketImport") String pTicketImport,
                               @Nullable @RequestParam("supplier") String pSupplier,
                               @Nullable @RequestParam("code") String pCode,
                               @Nullable @RequestParam("name") String pName,
                               @Nullable @RequestParam("unit") String pUnit,
                               @Nullable @RequestParam("location") String pLocation,
                               @Nullable @RequestParam("status") String pStatus) {
        validateModuleStorage.readMaterial(true);
        List<TicketImportGoods> listTicketImport = ticketImportService.findAll();
        List<Supplier> listSupplier = supplierService.findAll();
        List<Category> listUnit = categoryService.findSubCategory(AppConstants.UNIT);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.STG_MATERIAL);
        modelAndView.addObject("material", new Material());
        modelAndView.addObject("listMaterial", materialService.findAll());
        modelAndView.addObject("listDonViTinh", listUnit);
        modelAndView.addObject("templateImportName", "Name");
        if (pTicketImport != null) {
            List<TicketImportGoods> ticketImportFilter = new ArrayList<>();
            ticketImportFilter.add(new TicketImportGoods(CommonUtil.getIdFromRequestParam(pTicketImport), CommonUtil.getNameFromRequestParam(pTicketImport)));
            ticketImportFilter.addAll(listTicketImport);
            modelAndView.addObject("filter_ticketImport", ticketImportFilter);
        } else {
            modelAndView.addObject("filter_ticketImport", listTicketImport);
        }
        if (pSupplier != null) {
            List<Supplier> supplierFilter = new ArrayList<>();
            supplierFilter.add(new Supplier(CommonUtil.getIdFromRequestParam(pSupplier), CommonUtil.getNameFromRequestParam(pSupplier)));
            supplierFilter.addAll(listSupplier);
            modelAndView.addObject("filter_supplier", supplierFilter);
        } else {
            modelAndView.addObject("filter_supplier", listSupplier);
        }
        if (pUnit != null) {
            List<Category> unitFilter = new ArrayList<>();
            unitFilter.add(new Category(CommonUtil.getIdFromRequestParam(pUnit), CommonUtil.getNameFromRequestParam(pUnit)));
            unitFilter.addAll(listUnit);
            modelAndView.addObject("filter_unit", unitFilter);
        } else {
            modelAndView.addObject("filter_unit", listUnit);
        }
        modelAndView.addObject("filter_code", pCode);
        modelAndView.addObject("filter_name", pName);
        modelAndView.addObject("filter_location", pLocation);
        modelAndView.addObject("filter_status", pStatus);
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