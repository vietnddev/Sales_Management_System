package com.flowiee.app.controller.storage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/storage")
public class StorageController {
    @GetMapping(value = "")
    public String getAllStorageDoc(ModelMap modelMap){
        //
        return "pages/category/home";
    }
}
