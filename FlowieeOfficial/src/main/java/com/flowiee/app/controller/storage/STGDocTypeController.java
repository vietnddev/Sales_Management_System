package com.flowiee.app.controller.storage;

import com.flowiee.app.services.STGDocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("storage/doctype")
public class STGDocTypeController {
    @Autowired
    STGDocTypeService stgDocTypeService;

    @GetMapping("")
    public String getAllDocType(ModelMap modelMap){
        modelMap.addAttribute("listDocType", stgDocTypeService.getAllSTGDocType());
        return "pages/storage/doctype";
    }
}
