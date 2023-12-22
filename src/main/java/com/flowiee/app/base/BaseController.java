package com.flowiee.app.base;

import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.app.service.NotificationService;

import java.util.Objects;

@Component
public class BaseController {
	@Autowired
	protected AccountService accountService;
	@Autowired
	protected NotificationService notificationService;
	
	protected ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtil.getCurrentAccountUsername()));
		modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(CommonUtil.getCurrentAccountId()));
		return this.setURLSidebar(modelAndView);
	}
	
	protected ModelAndView baseView(ModelAndView modelAndView, boolean flag) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtil.getCurrentAccountUsername()));
		if (flag) modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(CommonUtil.getCurrentAccountId()));
		return this.setURLSidebar(modelAndView);
	}
	
	private ModelAndView setURLSidebar(ModelAndView modelAndView) {
		modelAndView.addObject("URL_CATEGORY", EndPointUtil.CATEGORY);
		modelAndView.addObject("URL_PRODUCT", EndPointUtil.PRO_PRODUCT);
		modelAndView.addObject("URL_PRODUCT_ORDER", EndPointUtil.PRO_ORDER);
		modelAndView.addObject("URL_PRODUCT_CUSTOMER", EndPointUtil.PRO_CUSTOMER);
		modelAndView.addObject("URL_PRODUCT_SUPPLIER", EndPointUtil.PRO_SUPPLIER);
		modelAndView.addObject("URL_PRODUCT_GALLERY", EndPointUtil.PRO_GALLERY);
		modelAndView.addObject("URL_STORAGE_DASHBOARD", EndPointUtil.STORAGE);
		modelAndView.addObject("URL_STORAGE_DOCUMENT", EndPointUtil.STORAGE_DOCUMENT);
		modelAndView.addObject("URL_STORAGE_MATERIAL", EndPointUtil.STORAGE_MATERIAL);
		modelAndView.addObject("URL_SYSTEM_CONFIG", EndPointUtil.SYS_CONFIG);
		modelAndView.addObject("URL_SYSTEM_ROLE", EndPointUtil.SYS_ROLE);
		modelAndView.addObject("URL_SYSTEM_LOG", EndPointUtil.SYS_LOG);
		modelAndView.addObject("URL_SYSTEM_ACCOUNT", EndPointUtil.SYS_ACCOUNT);
		return modelAndView;
	}
}