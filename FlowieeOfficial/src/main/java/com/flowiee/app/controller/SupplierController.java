package com.flowiee.app.controller;

import com.flowiee.app.config.author.ValidateModuleProduct;
import com.flowiee.app.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.service.SupplierService;
import com.flowiee.app.service.AccountService;

@Controller
@RequestMapping(path = "/product/supplier")
public class SupplierController extends BaseController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ValidateModuleProduct validateModuleProduct;

	@GetMapping
	public ModelAndView viewAllProducts() {
		if (!accountService.isLogin()) {
			return new ModelAndView(PagesUtil.SYS_LOGIN);
		}
		ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_SUPPLIER);
		modelAndView.addObject("supplier", new Supplier());
		modelAndView.addObject("listSuplier", supplierService.findAll());		
		return baseView(modelAndView);
	}

	@PostMapping("/insert")
	public String insertSupplier(@ModelAttribute("supplier") Supplier supplier) {
		if (!accountService.isLogin()) {
			return PagesUtil.SYS_LOGIN;
		}
		if (!validateModuleProduct.insertSupplier()) {
			return PagesUtil.SYS_UNAUTHORIZED;
		}
		if (supplier == null) {
			throw new NotFoundException("Customer not found!");
		}
		supplierService.save(supplier);
		return "redirect:/product/supplier";
	}
}