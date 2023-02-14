package com.flowiee.app.model.sales;

public class ProductDTO {
	int soLuong = 1;
	private Product product;
	
	public ProductDTO() {}
	
	public ProductDTO(Product product) {
		super();
		this.product = product;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soluong) {
		this.soLuong = soluong;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
