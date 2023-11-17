package com.flowiee.app.exception;

import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ModelAndView exceptionHandler(NotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_ERROR);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(BadRequestException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_ERROR);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(DataExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_ERROR);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_ERROR);
        modelAndView.addObject("error", error);
        return modelAndView;
    }
}