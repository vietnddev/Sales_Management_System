package com.flowiee.app.controller.storage;

import com.flowiee.app.model.storage.Storage;
import com.flowiee.app.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/storage")
public class StorageController {
    @Autowired
    StorageService storageService;

    private final int ID_ROOT = 0;

    @GetMapping(value = "/root")
    public String getRootDoc(ModelMap modelMap){
        //modelMap.addAttribute("listRootDoc", storageService.getRootDoc(0, 1));
        //System.out.println(modelMap.addAttribute("listRootDoc", storageService.getRootDoc(0, 1)));
        List<Storage> list = storageService.getRootDoc(0, 1);
        for (Storage s : list) {
            System.out.println(s.getName());
        }

        modelMap.addAttribute("listDoc", storageService.getRootDoc(0 ,1));
        return "pages/storage/docs";
    }
}
