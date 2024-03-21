package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.utils.EndPointUtil;
import com.flowiee.sms.utils.PagesUtils;
import com.flowiee.sms.entity.Account;
import com.flowiee.sms.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping
public class ProfileControllerUI extends BaseController {
	@Autowired private OrderService orderService;

	@GetMapping(EndPointUtil.SYS_PROFILE)
	public ModelAndView showInformation(@ModelAttribute("message") String message) {
		ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_PROFILE);
		modelAndView.addObject("message", message);
		modelAndView.addObject("profile", accountService.findCurrentAccount());
		modelAndView.addObject("listDonHangDaBan", orderService.findByStaffId(Objects.requireNonNull(CommonUtils.getUserPrincipal().getId())));
		return baseView(modelAndView);
	}

	@PostMapping(EndPointUtil.SYS_PROFILE_UPDATE)
	public ModelAndView updateProfile(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("account") Account accountEntity) {
		String username = userDetails.getUsername();
		String password = accountService.findByUsername(username).getPassword();
		int accountID = accountService.findByUsername(username).getId();

		accountEntity.setId(accountID);
		accountEntity.setUsername(username);
		accountEntity.setPassword(password);
		accountEntity.setStatus(true);
		accountService.save(accountEntity);

		return new ModelAndView("redirect:/profile");
	}

	@PostMapping(EndPointUtil.SYS_PROFILE_CHANGEPASS)
	public ModelAndView changePassword(HttpServletRequest request,
									   @ModelAttribute("account") Account accountEntity,
									   RedirectAttributes redirectAttributes) {
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
				accountEntity.setFullName(accountService.findByUsername(profile.getUsername()).getFullName());
				accountEntity.setPassword(bCrypt.encode(password_new));
				accountEntity.setStatus(true);
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