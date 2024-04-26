package com.flowiee.pms.controller.system;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.PagesUtils;
import com.flowiee.pms.entity.system.Account;

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
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping
public class ProfileControllerView extends BaseController {
	@Autowired
	private AccountService accountService;

	@GetMapping("/sys/profile")
	public ModelAndView showInformation(@ModelAttribute("message") String message) {
		ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_PROFILE);
		modelAndView.addObject("message", message);
		modelAndView.addObject("profile", accountService.findById(CommonUtils.getUserPrincipal().getId()));
		modelAndView.addObject("listDonHangDaBan", new ArrayList<Order>());
		return baseView(modelAndView);
	}

	@PostMapping( "/sys/profile/update")
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

	@PostMapping("/sys/profile/change-password")
	public ModelAndView changePassword(HttpServletRequest request,
									   @ModelAttribute("account") Account accountEntity,
									   RedirectAttributes redirectAttributes) {
		String password_old = request.getParameter("password_old");
		String password_new = request.getParameter("password_new");
		String password_renew = request.getParameter("password_renew");

		Optional<Account> profile = accountService.findById(CommonUtils.getUserPrincipal().getId());
		if (profile.isEmpty()) {
			throw new BadRequestException();
		}

		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
		if (bCrypt.matches(password_old, accountService.findByUsername(profile.get().getUsername()).getPassword())) {
			if (password_new.equals(password_renew)) {
				accountEntity.setId(accountService.findByUsername(profile.get().getUsername()).getId());
				accountEntity.setUsername(profile.get().getUsername());
				accountEntity.setFullName(accountService.findByUsername(profile.get().getUsername()).getFullName());
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