package com.flowiee.pms.controller.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.utils.PagesUtils;
import com.flowiee.pms.service.sales.SupplierService;

@Controller
@RequestMapping("/san-pham/supplier")
public class SupplierControllerView extends BaseController {
	@Autowired private SupplierService  supplierService;

	@GetMapping
	@PreAuthorize("@vldModuleSales.readSupplier(true)")
	public ModelAndView viewAllSupplier() {
		ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_SUPPLIER);
		modelAndView.addObject("listSupplier", supplierService.findAll(-1, -1).getContent());
		return baseView(modelAndView);
	}
}