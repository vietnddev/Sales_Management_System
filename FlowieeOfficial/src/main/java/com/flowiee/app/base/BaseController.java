package com.flowiee.app.base;

import com.flowiee.app.common.utils.EndPointUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.service.system.NotificationService;

@Component
public class BaseController {
	@Autowired
	private NotificationService notificationService;
	
	public ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
		//sidebar
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
		modelAndView.addObject("URL_PRODUCT_", "");
		modelAndView.addObject("URL_PRODUCT_", "");
		modelAndView.addObject("URL_PRODUCT_", "");
		modelAndView.addObject("URL_PRODUCT_", "");
		modelAndView.addObject("URL_PRODUCT_", "");
		modelAndView.addObject("URL_STORAGE_", "");
		modelAndView.addObject("URL_STORAGE_", "");
		modelAndView.addObject("URL_STORAGE_", "");
		modelAndView.addObject("URL_STORAGE_", "");
		modelAndView.addObject("URL_STORAGE_", "");
		modelAndView.addObject("URL_SYSTEM_CONFIG", EndPointUtil.SYSTEM_CONFIG_VIEW);
		modelAndView.addObject("URL_SYSTEM_ROLE", EndPointUtil.SYSTEM_ROLE_VIEW);
		modelAndView.addObject("URL_SYSTEM_LOG", EndPointUtil.SYSTEM_LOG_VIEW);
		modelAndView.addObject("URL_SYSTEM_ACCOUNT", EndPointUtil.SYSTEM_ACCOUNT_VIEW);
		return modelAndView;
	}
}