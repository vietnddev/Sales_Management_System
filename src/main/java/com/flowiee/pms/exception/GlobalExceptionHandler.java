package com.flowiee.pms.exception;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.AppResponse;
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
    public AppResponse<String> exceptionHandler(BadRequestException ex) {
        logger.error(ex.getMessage(), ex);
        return fail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    public AppResponse<?> exceptionHandler(DataExistsException ex) {
        logger.error(ex.getMessage(), ex);
        return fail(HttpStatus.CONFLICT, ex.getMessage());
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
    public AppResponse<?> exceptionHandler(DataInUseException ex) {
        logger.error(ex.getMessage(), ex);
        return fail(HttpStatus.LOCKED, ex.getMessage());
    }

    @ExceptionHandler
    public AppResponse<?> exceptionHandler(AppException ex) {
        logger.info(ex.getMessage(), ex);
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
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
    public AppResponse<?> exceptionHandler(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler
    public AppResponse<?> exceptionHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}