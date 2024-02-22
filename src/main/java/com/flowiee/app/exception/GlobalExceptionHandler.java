package com.flowiee.app.exception;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.utils.PagesUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler
    public ModelAndView exceptionHandler(AuthenticationException ex) {
        logger.error("AuthenticationException", ex);
        return new ModelAndView(PagesUtils.SYS_LOGIN);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(NotFoundException ex) {
        logger.error("NotFoundException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(BadRequestException ex) {
        return ApiResponse.fail(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(DataExistsException ex) {
        return ApiResponse.fail(ex.getMessage(), ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(ForbiddenException ex) {
        logger.error("ForbiddenException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(DataInUseException ex) {
        return ApiResponse.fail(ex.getMessage(), ex, HttpStatus.LOCKED);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(AppException ex) {
        return ApiResponse.fail(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex) {
        logger.error("Exception", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(RuntimeException ex) {
        return ApiResponse.fail(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}