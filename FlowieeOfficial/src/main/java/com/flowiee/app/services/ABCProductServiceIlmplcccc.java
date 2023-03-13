package com.flowiee.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flowiee.app.model.sales.Products;

@Service
public class ABCProductServiceIlmplcccc implements ABCProductService{

	@Autowired
	ABCProductRepo abcProductRepo; 
	
	@Override 
	public List<Products> getcAllProducttt() {

		return abcProductRepo.findAll();
	}
	

}
