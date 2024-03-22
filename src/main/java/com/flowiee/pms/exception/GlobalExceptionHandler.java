package com.flowiee.pms.exception;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.ApiResponse;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler
    public ModelAndView exceptionHandler(AuthenticationException ex) {
        logger.error(ex.getMessage(), ex);
        return new ModelAndView(PagesUtils.SYS_LOGIN);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(NotFoundException ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ApiResponse<String> exceptionHandler(BadRequestException ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.fail("Fail", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(DataExistsException ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.fail(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(ForbiddenException ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(DataInUseException ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.fail(ex.getMessage(), HttpStatus.LOCKED);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(AppException ex) {
        logger.info(ex.getMessage(), ex);
        return ApiResponse.fail(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler
//    public ModelAndView exceptionHandler(Exception ex) {
//        logger.error("Exception2", ex);
//        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
//        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ERROR);
//        modelAndView.addObject("error", error);
//        return baseView(modelAndView);
//    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.fail(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ApiResponse<?> exceptionHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return ApiResponse.fail(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}