package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.entity.Notification;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.author.ValidateModuleSystem;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sys")
public class SystemController extends BaseController {
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private OrderService orderService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    ValidateModuleSystem validateModuleSystem;

    @GetMapping("/nhom-quyen")
    public ModelAndView readRole() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleSystem.readPermission()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ROLE);
        modelAndView.addObject("listRole", roleService.findAllRole());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.getCurrentAccountId()));
        return baseView(modelAndView);
    }

    @GetMapping(value = "/nhat-ky")
    public ModelAndView getAllLog() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleSystem.readLog()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_LOG);
        modelAndView.addObject("listLog", systemLogService.getAll());
        return baseView(modelAndView);
    }

    @GetMapping("/config")
    public ModelAndView showConfig() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleSystem.setupConfig()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_CONFIG);
        modelAndView.addObject("config", new FlowieeConfig());
        modelAndView.addObject("listConfig", configService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping("/config/update/{id}")
    public String update(@ModelAttribute("config") FlowieeConfig config,
                         @PathVariable("id") Integer configId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSystem.setupConfig()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (configId <= 0 || configService.findById(configId) == null) {
            throw new NotFoundException("Config not found!");
        }
        configService.update(config, configId);
        return "redirect:/he-thong/config";
    }

    @GetMapping("/notification")
    public ModelAndView getAllNotification() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_NOTIFICATION);
        modelAndView.addObject("notification", new Notification());
        return baseView(modelAndView);
    }

    @GetMapping(value = "/login")
    public String showLoginPage() {
        if (accountService.findByUsername("admin") == null) {
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("$2a$12$UGPx1eE9SzfvCDniYtwoZuQRzVdjHKkjbZcDKXO4.1Z/uGpOsFFVu");
            account.setHoTen("Quản trị hệ thống");
            account.setEmail("nguyenducviet0684@gmail.com");
            account.setSoDienThoai("0706820684");
            account.setGioiTinh(true);
            account.setTrangThai(true);
            account.setCreatedBy(0);
            accountService.save(account);
        }
        return "login";
    }

    @GetMapping(value = "/change-password")
    public String showPageChangePassword() {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        return PagesUtil.SYS_UNAUTHORIZED;
    }
    @GetMapping(value = "/change-password", params = "submit")
    public void submitChangePassword(){
        //
    }

    @GetMapping(value = "/forgot-password")
    public void showPageForgotPassword(){
        //Send password to email register
    }

    @GetMapping(value = "/reset-password")
    public void resetPassword(){
        //Send password to email register
    }

    @GetMapping("/profile")
    public ModelAndView viewProfile(@ModelAttribute("message") String message) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_PROFILE);
        modelAndView.addObject("message", message);
        modelAndView.addObject("profile", accountService.findCurrentAccount());
        modelAndView.addObject("listDonHangDaBan", orderService.findByNhanVienId(FlowieeUtil.getCurrentAccountId()));
        return baseView(modelAndView);
    }

    @PostMapping("/profile/update")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @ModelAttribute("account") Account accountEntity) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        String username = userDetails.getUsername();
        String password = accountService.findByUsername(username).getPassword();
        int accountID = accountService.findByUsername(username).getId();

        accountEntity.setId(accountID);
        accountEntity.setUsername(username);
        accountEntity.setPassword(password);
        accountEntity.setTrangThai(true);
        accountService.save(accountEntity);

        return "redirect:/profile";
    }

    @PostMapping("/profile/change-password")
    public ModelAndView changePassword(HttpServletRequest request,
                                       @ModelAttribute("account") Account accountEntity,
                                       RedirectAttributes redirectAttributes) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        String password_old = request.getParameter("password_old");
        String password_new = request.getParameter("password_new");
        String password_renew = request.getParameter("password_renew");

        Account profile = accountService.findCurrentAccount();

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if (bCrypt.matches(password_old,
                accountService.findByUsername(profile.getUsername()).getPassword())) {
            if (password_new.equals(password_renew)) {
                accountEntity.setId(accountService.findByUsername(profile.getUsername()).getId());
                accountEntity.setUsername(profile.getUsername());
                accountEntity.setHoTen(accountService.findByUsername(profile.getUsername()).getHoTen());
                accountEntity.setPassword(bCrypt.encode(password_new));
                accountEntity.setTrangThai(true);
                accountService.save(accountEntity);

                redirectAttributes.addAttribute("message", "Cập nhật thành công!");
                RedirectView redirectView = new RedirectView();
                redirectView.setUrl("/profile");
                return new ModelAndView(redirectView);
            }
            redirectAttributes.addAttribute("message", "Mật khẩu nhập lại chưa khớp!");
        }
        redirectAttributes.addAttribute("message", "Sai mật khẩu hiện tại!");

        return new ModelAndView("redirect:/profile");
    }
}
