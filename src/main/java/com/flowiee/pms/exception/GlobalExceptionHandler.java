package com.flowiee.pms.exception;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.utils.constants.Pages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler
    public ModelAndView exceptionHandler(AuthenticationException ex) {
        mvLogger.error(ex.getMessage(), ex);
        return new ModelAndView(Pages.SYS_LOGIN.getTemplate());
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<Object>> exceptionHandler(ResourceNotFoundException ex) {
        mvLogger.error(ex.getMessage(), ex);
        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(Pages.SYS_ERROR.getTemplate());
        modelAndView.addObject("error", error);
        return ResponseEntity.badRequest().body(fail(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<Object>> exceptionHandler(BadRequestException ex) {
        mvLogger.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(fail(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<?>> exceptionHandler(DataExistsException ex) {
        mvLogger.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(fail(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(ForbiddenException ex) {
        mvLogger.error(ex.getMessage(), ex);
        ErrorModel error = new ErrorModel(HttpStatus.FORBIDDEN.value(), ex.getMessage());
        ModelAndView modelAndView = new ModelAndView(Pages.SYS_ERROR.getTemplate());
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<Object>> exceptionHandler(DataInUseException ex) {
        mvLogger.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError ().body(fail(HttpStatus.LOCKED, ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<Object>> exceptionHandler(AppException ex) {
        mvLogger.info(ex.getMessage(), ex);
        return ResponseEntity.internalServerError ().body(fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<?>> exceptionHandler(RuntimeException ex) {
        mvLogger.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<AppResponse<?>> exceptionHandler(Exception ex) {
        mvLogger.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(fail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}