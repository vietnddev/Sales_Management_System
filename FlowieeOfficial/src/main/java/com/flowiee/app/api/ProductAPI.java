package com.flowiee.app.api;

import com.flowiee.app.model.admin.Log;
import com.flowiee.app.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductAPI {
    @Autowired
    LogService logService;

    @GetMapping("")
    @ResponseBody
    public List<Log> getAllLog(){
        List<Log> list = logService.getAll();
        return list;
    }
}
