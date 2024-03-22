package com.flowiee.pms.base;

import com.flowiee.pms.model.ApiResponse;
import com.flowiee.pms.model.PaginationModel;
import com.flowiee.pms.utils.EndPointUtil;
import com.flowiee.pms.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class BaseController<T> {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtils.getUserPrincipal().getUsername()));
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

	protected static <T> ApiResponse<T> success(@NonNull T data) {
		return success(data, "OK");
	}

	protected static <T> ApiResponse<T> success(@NonNull T data, String message) {
		return success(data, message, null);
	}

	protected static <T> ApiResponse<T> success(@NonNull T data, int pageNum, int pageSize, int totalPage, long totalElements) {
		return success(data, "OK", pageNum, pageSize, totalPage, totalElements);
	}

	protected static <T> ApiResponse<T> success(@NonNull T data, String message, int pageNum, int pageSize, int totalPage, long totalElements) {
		return success(data, message, new PaginationModel(pageNum, pageSize, totalPage, totalElements));
	}

	protected static <T> ApiResponse<T> success(@NonNull T data, String message, PaginationModel pagination) {
		return new ApiResponse<>(true, HttpStatus.OK, message, data, pagination);
	}

	protected static <T> ApiResponse<T> fail(@NonNull HttpStatus httpStatus) {
		return fail(httpStatus, "NOK");
	}

	protected static <T> ApiResponse<T> fail(@NonNull HttpStatus httpStatus, @NonNull String message) {
		return new ApiResponse<>(false, httpStatus, message, null, null);
	}
}