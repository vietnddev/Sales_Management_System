package com.flowiee.app.controller.sales;

import com.flowiee.app.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/sales/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping(value = "")
    public String getAllCustomer(ModelMap modelMap){
        modelMap.addAttribute("listCustomer", customerService.getAll());
        return "pages/sales/customer";
    }
}
