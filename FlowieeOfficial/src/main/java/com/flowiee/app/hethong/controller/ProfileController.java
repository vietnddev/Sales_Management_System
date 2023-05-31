package com.flowiee.app.hethong.controller;

import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.file.service.FileStorageService;
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
	private FileStorageService filessService;

	BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

	@GetMapping("")
	public String showInformation(@AuthenticationPrincipal UserDetails userDetails, ModelMap modelMap) {
		// Lấy thông tin của user đã đăng nhập
		Account accountEntity = accountService.findByUsername(userDetails.getUsername());
		if (accountEntity != null) {
			modelMap.addAttribute("information", accountEntity);
		}
		
		modelMap.addAttribute("account", new Account());

		return "pages/profile/information";
	}

	@PostMapping("/update")
	public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute("account") Account accountEntity) {
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
	public String changePassword(HttpServletRequest request, @AuthenticationPrincipal UserDetails userDetails,
                                 @ModelAttribute("account") Account accountEntity, ModelMap modelMap) {
		String password_old = request.getParameter("password_old");
		String password_new = request.getParameter("password_new");
		String password_renew = request.getParameter("password_renew");

		if (bCrypt.matches(password_old,
				accountService.findByUsername(userDetails.getUsername()).getPassword())) {
			if (password_new.equals(password_renew)) {
				accountEntity.setId(accountService.findByUsername(userDetails.getUsername()).getId());
				accountEntity.setUsername(userDetails.getUsername());
				accountEntity.setHoTen(accountService.findByUsername(userDetails.getUsername()).getHoTen());
				accountEntity.setPassword(bCrypt.encode(password_new));
				accountEntity.setTrangThai(true);
				accountService.save(accountEntity);
				
				modelMap.addAttribute("successMessage", "OK rồi nhé");
			}		 			
			modelMap.addAttribute("successMessage", "OK rồi nhée");
		}  
		modelMap.addAttribute("successMessage", "OK rồi nhée");
		
		return "redirect:/profile";
		//return "pages/profile/information";
	}  
}   