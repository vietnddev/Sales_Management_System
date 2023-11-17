package com.flowiee.app.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.service.product.SupplierService;
import com.flowiee.app.service.system.AccountService;

@Controller
@RequestMapping(path = "/product/supplier")
public class SupplierController extends BaseController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private SupplierService supplierService;

	@GetMapping(value = "")
	public ModelAndView viewAllProducts() {
		if (!accountService.isLogin()) {
			return new ModelAndView(PagesUtil.PAGE_LOGIN);
		}
		ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_SANPHAM_SUPPLIER);
		modelAndView.addObject("supplier", new Supplier());
		modelAndView.addObject("listSuplier", supplierService.findAll());		
		return baseView(modelAndView);
	}
}