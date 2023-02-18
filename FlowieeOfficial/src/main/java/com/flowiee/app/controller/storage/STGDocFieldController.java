package com.flowiee.app.controller.storage;

import com.flowiee.app.services.STGDocFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/storage/doctype")
public class STGDocFieldController {
    @Autowired
    STGDocFieldService stgDocFieldService;

    @RequestMapping("/docfield-{ID}")
    public String getDocField(ModelMap modelMap, @PathVariable("ID") int IDDocType){
        System.out.println("IDDDDDDDDDDDDDDDDdd " + IDDocType);
        modelMap.addAttribute("listDocField", stgDocFieldService.getByIDDocType(IDDocType));
        return "pages/storage/docfield";
    }
}
