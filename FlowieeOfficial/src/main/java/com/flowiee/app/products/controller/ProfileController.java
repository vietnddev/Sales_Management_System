package com.flowiee.app.products.controller;

import com.flowiee.app.nguoidung.entity.AccountEntity;
import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.products.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/profile")
public class ProfileController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private FileService filessService;

	BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

	@GetMapping("")
	public String showInformation(@AuthenticationPrincipal UserDetails userDetails, ModelMap modelMap) {
		// Lấy thông tin của user đã đăng nhập
		AccountEntity accountEntity = accountService.getAccountByUsername(userDetails.getUsername());
		if (accountEntity != null) {
			modelMap.addAttribute("information", accountEntity);
		}
		
		modelMap.addAttribute("account", new AccountEntity());

		return "pages/profile/information";
	}

	@PostMapping("/update")
	public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute("account") AccountEntity accountEntity) {
		String username = userDetails.getUsername();
		String password = accountService.getAccountByUsername(username).getPassword();
		int accountID = accountService.getAccountByUsername(username).getId();

		accountEntity.setId(accountID);
		accountEntity.setUsername(username);
		accountEntity.setPassword(password);
		accountEntity.setTrangThai(true);
		accountService.saveAccount(accountEntity);

		return "redirect:/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails,
                                 @ModelAttribute("account") AccountEntity accountEntity, ModelMap modelMap) {
		String password_old = request.getParameter("password_old");
		String password_new = request.getParameter("password_new");
		String password_renew = request.getParameter("password_renew");

		if (bCrypt.matches(password_old,
				accountService.getAccountByUsername(userDetails.getUsername()).getPassword())) {
			if (password_new.equals(password_renew)) {
				accountEntity.setId(accountService.getAccountByUsername(userDetails.getUsername()).getId());
				accountEntity.setUsername(userDetails.getUsername());
				accountEntity.setHoTen(accountService.getAccountByUsername(userDetails.getUsername()).getHoTen());
				accountEntity.setPassword(bCrypt.encode(password_new));
				accountEntity.setTrangThai(true);
				accountService.saveAccount(accountEntity);
				
				modelMap.addAttribute("successMessage", "OK rồi nhé");
			}		 			
			modelMap.addAttribute("successMessage", "OK rồi nhée");
		}  
		modelMap.addAttribute("successMessage", "OK rồi nhée");
		
		return "redirect:/profile";
		//return "pages/profile/information";
	}  
}   