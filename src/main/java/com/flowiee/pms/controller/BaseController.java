package com.flowiee.pms.controller;

import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.CategoryType;
import lombok.Getter;
import lombok.Setter;
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
	@Getter
	@Setter
	class SearchTool {
		private boolean enableFilter;
		private List<String> filters;

		SearchTool() {
			enableFilter = false;
		}
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private SearchTool searchTool;

	protected ModelAndView baseView(ModelAndView modelAndView) {
		SearchTool searchTool = getSearchTool();
		modelAndView.addObject("configSearchTool", searchTool != null ? searchTool : new SearchTool());
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

	protected void setupSearchTool(boolean pEnableFilter, List<Object> pFilters) {
		searchTool = new SearchTool();
		searchTool.setEnableFilter(pEnableFilter);
		List<String> filters = new ArrayList<>();
		if (pFilters != null) {
			for (Object obj : pFilters) {
				if (obj instanceof CategoryType) {
					filters.add(((CategoryType) obj).name());
				} else if (obj instanceof String) {
					filters.add(obj.toString());
				}
			}
		}
		searchTool.setFilters(filters);
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