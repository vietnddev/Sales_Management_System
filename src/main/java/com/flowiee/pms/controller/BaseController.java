package com.flowiee.pms.controller;

import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.utils.CommonUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private List<String> listOfFilters = new ArrayList<>();

	protected ModelAndView baseView(ModelAndView modelAndView) {
		modelAndView.addObject("listOfFilters", getListOfFilters());
		modelAndView.addObject("USERNAME_LOGIN", CommonUtils.getUserPrincipal().getUsername());
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

	protected void setupFilters(List<String> pFilters) {
		listOfFilters.clear();
		listOfFilters.addAll(pFilters);
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