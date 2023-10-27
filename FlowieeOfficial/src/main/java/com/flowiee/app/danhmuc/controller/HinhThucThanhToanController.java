package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.EndPointUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.danhmuc.service.HinhThucThanhToanService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/hinh-thuc-thanh-toan")
public class HinhThucThanhToanController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public ModelAndView findAll() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DANHMUC_HINHTHUC_THANHTOAN);
            List<HinhThucThanhToan> list = hinhThucThanhToanService.findAll();
            modelAndView.addObject("listDanhMuc", list);
            modelAndView.addObject("hinhThucThanhToan", new HinhThucThanhToan());
            modelAndView.addObject("templateImportName", FileUtil.TEMPLATE_IE_DM_LOAIHINHTHUCTHANHTOAN);
            modelAndView.addObject("url_template", EndPointUtil.DANHMUC_HINHTHUCTHANHTOAN_TEMPLATE);
            modelAndView.addObject("url_import", EndPointUtil.DANHMUC_HINHTHUCTHANHTOAN_IMPORT);
            modelAndView.addObject("url_export", EndPointUtil.DANHMUC_HINHTHUCTHANHTOAN_EXPORT);
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("hinhThucThanhToan") HinhThucThanhToan hinhThucThanhToan) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        hinhThucThanhToanService.save(hinhThucThanhToan);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("hinhThucThanhToan") HinhThucThanhToan hinhThucThanhToan,
                         @PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        hinhThucThanhToanService.update(hinhThucThanhToan, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        hinhThucThanhToanService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/template")
    public ResponseEntity<?> exportTemplate() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
            byte[] dataExport = hinhThucThanhToanService.exportTemplate();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAIHINHTHUCTHANHTOAN + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/import")
    public String importData(@RequestParam("file") MultipartFile file) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
            hinhThucThanhToanService.importData(file);
            return "redirect:" + EndPointUtil.DANHMUC_HINHTHUCTHANHTOAN_VIEW;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportData() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenExport()) {
            byte[] dataExport = hinhThucThanhToanService.exportData();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAIHINHTHUCTHANHTOAN + ".xlsx");
            return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}