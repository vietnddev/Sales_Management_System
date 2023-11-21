package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.entity.Account;
import com.flowiee.app.service.product.OrderService;
import com.flowiee.app.service.system.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController extends BaseController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;	

	@GetMapping
	public ModelAndView showInformation(@ModelAttribute("message") String message) {
		if (!accountService.isLogin()) {
			return new ModelAndView(PagesUtil.SYS_LOGIN);
		}
		ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_PROFILE);
		modelAndView.addObject("message", message);
		modelAndView.addObject("profile", accountService.findCurrentAccount());
		modelAndView.addObject("listDonHangDaBan", orderService.findByNhanVienId(accountService.findCurrentAccountId()));
		return baseView(modelAndView);
	}

	@PostMapping("/update")
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

	@PostMapping("/change-password")
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