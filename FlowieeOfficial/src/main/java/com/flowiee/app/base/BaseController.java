package com.flowiee.app.base;

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
		return modelAndView;
	}
}