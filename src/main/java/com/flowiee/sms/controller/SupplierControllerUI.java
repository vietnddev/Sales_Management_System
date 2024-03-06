package com.flowiee.sms.controller;

import com.flowiee.sms.core.vld.ValidateModuleProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.utils.PagesUtils;
import com.flowiee.sms.service.SupplierService;

@Controller
@RequestMapping("/san-pham/supplier")
public class SupplierControllerUI extends BaseController {
	@Autowired private SupplierService supplierService;
	@Autowired private ValidateModuleProduct validateModuleProduct;

	@GetMapping
	public ModelAndView viewAllSupplier() {
		validateModuleProduct.readSupplier(true);
		ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_SUPPLIER);
		modelAndView.addObject("listSupplier", supplierService.findAll());
		return baseView(modelAndView);
	}
}