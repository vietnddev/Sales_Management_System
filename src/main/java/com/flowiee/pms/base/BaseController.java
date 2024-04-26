package com.flowiee.pms.base;

import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

@Component
public class BaseController {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("USERNAME_LOGIN", Objects.requireNonNull(CommonUtils.getUserPrincipal().getUsername()));
		setURLHeader(modelAndView);
		setURLSidebar(modelAndView);
		return modelAndView;
	}

	private void setURLHeader(ModelAndView modelAndView) {
		for (Map.Entry<String, String> entry : CommonUtils.mvEndPointHeaderConfig.entrySet()) {
			modelAndView.addObject(entry.getKey(), entry.getValue());
		}
	}
	
	private void setURLSidebar(ModelAndView modelAndView) {
		for (Map.Entry<String, String> entry : CommonUtils.mvEndPointSideBarConfig.entrySet()) {
			modelAndView.addObject(entry.getKey(), entry.getValue());
		}
	}

	protected static <T> AppResponse<T> success(@NonNull T data) {
		return success(data, "OK");
	}

	protected static <T> AppResponse<T> success(@NonNull T data, String message) {
		return success(data, message, null);
	}

	protected static <T> AppResponse<T> success(@NonNull T data, int pageNum, int pageSize, int totalPage, long totalElements) {
		return success(data, "OK", pageNum, pageSize, totalPage, totalElements);
	}

	protected static <T> AppResponse<T> success(@NonNull T data, String message, int pageNum, int pageSize, int totalPage, long totalElements) {
		return success(data, message, new AppResponse.PaginationModel(pageNum, pageSize, totalPage, totalElements));
	}

	protected static <T> AppResponse<T> success(@NonNull T data, String message, AppResponse.PaginationModel pagination) {
		return new AppResponse<>(true, HttpStatus.OK, message, data, pagination);
	}

	protected static <T> AppResponse<T> fail(@NonNull HttpStatus httpStatus) {
		return fail(httpStatus, "NOK");
	}

	protected static <T> AppResponse<T> fail(@NonNull HttpStatus httpStatus, @NonNull String message) {
		return new AppResponse<>(false, httpStatus, message, null, null);
	}
}