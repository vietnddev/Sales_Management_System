package com.flowiee.sms.core;

import com.flowiee.sms.core.vld.ValidateModuleCategory;
import com.flowiee.sms.core.vld.ValidateModuleStorage;
import com.flowiee.sms.core.vld.ValidateModuleSystem;
import com.flowiee.sms.utils.EndPointUtil;
import com.flowiee.sms.core.vld.ValidateModuleProduct;
import com.flowiee.sms.service.AccountService;
import com.flowiee.sms.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class BaseController {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired protected AccountService accountService;
	@Autowired protected ValidateModuleProduct vldModuleProduct;
	@Autowired protected ValidateModuleSystem vldModuleSystem;
	@Autowired protected ValidateModuleCategory vldModuleCategory;
	@Autowired protected ValidateModuleStorage vldModuleStorage;
	@Autowired protected BaseAuthorize baseAuthorize;

	protected ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtils.getCurrentAccountUsername()));
		setURLHeader(modelAndView);
		setURLSidebar(modelAndView);
		return modelAndView;
	}

	private void setURLHeader(ModelAndView modelAndView) {
		modelAndView.addObject("URL_PROFILE", EndPointUtil.SYS_PROFILE);
		modelAndView.addObject("URL_LOGOUT", EndPointUtil.SYS_LOGOUT);
	}
	
	private void setURLSidebar(ModelAndView modelAndView) {
		modelAndView.addObject("URL_CATEGORY", EndPointUtil.CATEGORY);
		modelAndView.addObject("URL_PRODUCT", EndPointUtil.PRO_PRODUCT);
		modelAndView.addObject("URL_PRODUCT_ORDER", EndPointUtil.PRO_ORDER);
		modelAndView.addObject("URL_PRODUCT_CUSTOMER", EndPointUtil.PRO_CUSTOMER);
		modelAndView.addObject("URL_PRODUCT_SUPPLIER", EndPointUtil.PRO_SUPPLIER);
		modelAndView.addObject("URL_PRODUCT_GALLERY", EndPointUtil.PRO_GALLERY);
		modelAndView.addObject("URL_STORAGE_DASHBOARD", EndPointUtil.STORAGE);
		modelAndView.addObject("URL_STORAGE_DOCUMENT", EndPointUtil.STORAGE_DOCUMENT);
		modelAndView.addObject("URL_STORAGE_MATERIAL", EndPointUtil.STORAGE_MATERIAL);
		modelAndView.addObject("URL_STORAGE_TICKET_IMPORT", EndPointUtil.STORAGE_TICKET_IMPORT);
		modelAndView.addObject("URL_STORAGE_TICKET_EXPORT", EndPointUtil.STORAGE_TICKET_EXPORT);
		modelAndView.addObject("URL_SYSTEM_CONFIG", EndPointUtil.SYS_CONFIG);
		modelAndView.addObject("URL_SYSTEM_LOG", EndPointUtil.SYS_LOG);
		modelAndView.addObject("URL_SYSTEM_ACCOUNT", EndPointUtil.SYS_ACCOUNT);
	}
}