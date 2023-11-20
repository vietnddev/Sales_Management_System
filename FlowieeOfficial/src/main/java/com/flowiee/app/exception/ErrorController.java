package com.flowiee.app.exception;

import com.flowiee.app.common.utils.NotificationUtil;
import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @GetMapping("/403")
    public ModelAndView forbiddenException() {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), NotificationUtil.ERROR_FORBIDDEN);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_ERROR);
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    @GetMapping("/404")
    public ModelAndView notfoundException() {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), NotificationUtil.ERROR_NOTFOUND);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_ERROR);
        modelAndView.addObject("error", error);
        return modelAndView;
    }
}