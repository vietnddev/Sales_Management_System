package com.flowiee.app.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/task")
public class TaskController {

    @RequestMapping(value = "")
    public String homePage() {
        return "home";
    }
}
