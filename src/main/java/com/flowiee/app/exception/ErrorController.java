package com.flowiee.app.exception;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.utils.MessagesUtil;
import com.flowiee.app.utils.PagesUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {
    @GetMapping("/403")
    public ModelAndView forbiddenException() {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), MessagesUtil.ERROR_FORBIDDEN);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }

    @GetMapping("/404")
    public ModelAndView notfoundException() {
        ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value(), MessagesUtil.ERROR_NOTFOUND);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ERROR);
        modelAndView.addObject("error", error);
        return baseView(modelAndView);
    }
}