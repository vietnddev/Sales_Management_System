package com.flowiee.app.exception;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.service.impl.VoucherInfoServiceImpl;
import com.flowiee.app.utils.PagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ModelAndView exceptionHandler(AuthenticationException ex) {
        logger.error("AuthenticationException", ex);        
        return new ModelAndView(PagesUtil.SYS_LOGIN);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(NotFoundException ex) {
        logger.error("NotFoundException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(BadRequestException ex) {
        logger.error("BadRequestException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(DataExistsException ex) {
        logger.error("DataInUseException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(ForbiddenException ex) {
        logger.error("ForbiddenException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(DataInUseException ex) {
        logger.error("DataInUseException", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.LOCKED.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex) {
        logger.error("Exception", ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }
}