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
		modelAndView.addObject("URL_CATEGORY", EndPointUtil.CATEGORY_VIEW);
		modelAndView.addObject("URL_PRODUCT", EndPointUtil.PRODUCT_VIEW);
		modelAndView.addObject("URL_PRODUCT_ORDER", EndPointUtil.PRODUCT_ORDER_VIEW);
		modelAndView.addObject("URL_PRODUCT_CUSTOMER", EndPointUtil.PRODUCT_CUSTOMER_VIEW);
		modelAndView.addObject("URL_PRODUCT_SUPPLIER", EndPointUtil.PRODUCT_SUPPLIER_VIEW);
		modelAndView.addObject("URL_PRODUCT_GALLERY", EndPointUtil.PRO_GALLERY);
		modelAndView.addObject("URL_STORAGE_DASHBOARD", EndPointUtil.STORAGE_DASHBOARD);
		modelAndView.addObject("URL_STORAGE_DOCUMENT", EndPointUtil.STORAGE_DOCUMENT_VIEW);
		modelAndView.addObject("URL_STORAGE_MATERIAL", EndPointUtil.STORAGE_MATERIAL_VIEW);
		modelAndView.addObject("URL_SYSTEM_CONFIG", EndPointUtil.SYS_CONFIG_VIEW);
		modelAndView.addObject("URL_SYSTEM_ROLE", EndPointUtil.SYS_ROLE_VIEW);
		modelAndView.addObject("URL_SYSTEM_LOG", EndPointUtil.SYS_LOG_VIEW);
		modelAndView.addObject("URL_SYSTEM_ACCOUNT", EndPointUtil.SYS_ACCOUNT_VIEW);
		return modelAndView;
	}
}