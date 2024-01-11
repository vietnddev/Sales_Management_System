package com.flowiee.app.base;

import com.flowiee.app.model.ResponseModel;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.app.service.NotificationService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class BaseController<T> {
	@Autowired
	protected AccountService accountService;
	@Autowired
	protected NotificationService notificationService;

	public ResponseModel<T> success(T data) {
		ResponseModel<T> responseModel = new ResponseModel<>();
		responseModel.setElements(Collections.singletonList(data));

		ResponseModel.Status status = new ResponseModel.Status();
		status.setSuccess(true);
		status.setCode(200);

		responseModel.setStatus(status);

		return responseModel;
	}

	public ResponseModel<T> success(List<T> data, Integer pageSize, Integer pageIndex, Integer pageCount, Long total) {
		ResponseModel<T> responseModel = new ResponseModel<>();
		responseModel.setElements(data);

		ResponseModel.Status status = new ResponseModel.Status();
		status.setSuccess(true);
		status.setCode(200);

		ResponseModel.Metadata metadata = new ResponseModel.Metadata();
		metadata.setPageSize(pageSize);
		metadata.setPageIndex(pageIndex);
		metadata.setPageCount(pageCount);
		metadata.setTotalEntity(total);

		responseModel.setStatus(status);
		responseModel.setMetadata(metadata);

		return responseModel;
	}

	public ResponseModel error(Integer code, String message){
		ResponseModel responseModel = new ResponseModel<>();

		ResponseModel.Status status = new ResponseModel.Status();
		status.setSuccess(false);
		status.setCode(code);
		status.setErrors(message);

		responseModel.setStatus(status);

		return responseModel;
	}

	protected ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtil.getCurrentAccountUsername()));
		modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(CommonUtil.getCurrentAccountId()));
		return setURLSidebar(setURLHeader(modelAndView));
	}
	
	protected ModelAndView baseView(ModelAndView modelAndView, boolean flag) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtil.getCurrentAccountUsername()));
		if (flag) modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(CommonUtil.getCurrentAccountId()));
		return setURLSidebar(setURLHeader(modelAndView));
	}

	private ModelAndView setURLHeader(ModelAndView modelAndView) {
		modelAndView.addObject("URL_PROFILE", EndPointUtil.SYS_PROFILE);
		modelAndView.addObject("URL_LOGOUT", EndPointUtil.SYS_LOGOUT);
		return modelAndView;
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
		modelAndView.addObject("URL_STORAGE_TICKET_IMPORT", EndPointUtil.STORAGE_TICKET_IMPORT);
		modelAndView.addObject("URL_STORAGE_TICKET_EXPORT", EndPointUtil.STORAGE_TICKET_EXPORT);
		modelAndView.addObject("URL_SYSTEM_CONFIG", EndPointUtil.SYS_CONFIG);
		modelAndView.addObject("URL_SYSTEM_ROLE", EndPointUtil.SYS_ROLE);
		modelAndView.addObject("URL_SYSTEM_LOG", EndPointUtil.SYS_LOG);
		modelAndView.addObject("URL_SYSTEM_ACCOUNT", EndPointUtil.SYS_ACCOUNT);
		return modelAndView;
	}
}