package com.flowiee.app.base;

import com.flowiee.app.common.utils.EndPointUtil;
import com.flowiee.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.app.service.NotificationService;

@Component
public class BaseController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private NotificationService notificationService;
	
	public ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(accountService.findCurrentAccountId()));
		//sidebar
		modelAndView.addObject("URL_CATEGORY", EndPointUtil.CATEGORY_VIEW);
		modelAndView.addObject("URL_CATEGORY_UNIT", EndPointUtil.CATEGORY_UNIT_VIEW);
		modelAndView.addObject("URL_CATEGORY_PAYMETHOD", EndPointUtil.CATEGORY_PAYMETHOD_VIEW);
		modelAndView.addObject("URL_CATEGORY_FABRICTYPE", EndPointUtil.CATEGORY_FABRICTYPE_VIEW);
		modelAndView.addObject("URL_CATEGORY_SALESCHANNEL", EndPointUtil.CATEGORY_SALESCHANNEL_VIEW);
		modelAndView.addObject("URL_CATEGORY_SIZE", EndPointUtil.CATEGORY_SIZE_VIEW);
		modelAndView.addObject("URL_CATEGORY_COLOR", EndPointUtil.CATEGORY_COLOR_VIEW);
		modelAndView.addObject("URL_CATEGORY_PRODUCTTYPE", EndPointUtil.CATEGORY_PRODUCTTYPE_VIEW);
		modelAndView.addObject("URL_CATEGORY_DOCUMENTTYPE", EndPointUtil.CATEGORY_DOCUMENTTYPE_VIEW);
		modelAndView.addObject("URL_CATEGORY_ORDERSTATUS", EndPointUtil.CATEGORY_ORDERSTATUS_VIEW);
		modelAndView.addObject("URL_CATEGORY_PAYMENTSTATUS", EndPointUtil.CATEGORY_PAYMETHOD_VIEW);
		modelAndView.addObject("URL_PRODUCT", EndPointUtil.PRODUCT_VIEW);
		modelAndView.addObject("URL_PRODUCT_ORDER", EndPointUtil.PRODUCT_ORDER_VIEW);
		modelAndView.addObject("URL_PRODUCT_CUSTOMER", EndPointUtil.PRODUCT_CUSTOMER_VIEW);
		modelAndView.addObject("URL_PRODUCT_SUPPLIER", EndPointUtil.PRODUCT_SUPPLIER_VIEW);
		modelAndView.addObject("URL_STORAGE_DASHBOARD", EndPointUtil.STORAGE_DASHBOARD);
		modelAndView.addObject("URL_STORAGE_DOCUMENT", EndPointUtil.STORAGE_DOCUMENT_VIEW);
		modelAndView.addObject("URL_STORAGE_MATERIAL", EndPointUtil.STORAGE_MATERIAL_VIEW);
		modelAndView.addObject("URL_SYSTEM_CONFIG", EndPointUtil.SYSTEM_CONFIG_VIEW);
		modelAndView.addObject("URL_SYSTEM_ROLE", EndPointUtil.SYSTEM_ROLE_VIEW);
		modelAndView.addObject("URL_SYSTEM_LOG", EndPointUtil.SYSTEM_LOG_VIEW);
		modelAndView.addObject("URL_SYSTEM_ACCOUNT", EndPointUtil.SYSTEM_ACCOUNT_VIEW);
		return modelAndView;
	}
}