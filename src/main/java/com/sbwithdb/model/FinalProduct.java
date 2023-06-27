package com.sbwithdb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class FinalProduct {

	private Long productId;

	private String productName;

	private Supplier supplierId;

	private Category categoryId;

	private int productQty;

	private double productPrice;

	private double finalProductPrice;

	private Charges charges;

	public FinalProduct() {
		// TODO Auto-generated constructor stub
	}

	public FinalProduct(Long productId, String productName, Supplier supplierId, Category categoryId, int productQty,
			double productPrice, double finalProductPrice, Charges charges) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.supplierId = supplierId;
		this.categoryId = categoryId;
		this.productQty = productQty;
		this.productPrice = productPrice;
		this.finalProductPrice = finalProductPrice;
		this.charges = charges;
	}
	
	

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Supplier getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Supplier supplierId) {
		this.supplierId = supplierId;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

	public int getProductQty() {
		return productQty;
	}

	public void setProductQty(int productQty) {
		this.productQty = productQty;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getFinalProductPrice() {
		return finalProductPrice;
	}

	public void setFinalProductPrice(double finalProductPrice) {
		this.finalProductPrice = finalProductPrice;
	}

	public Charges getCharges() {
		return charges;
	}

	public void setCharges(Charges charges) {
		this.charges = charges;
	}

	@Override
	public String toString() {
		return "FinalProduct [productId=" + productId + ", productName=" + productName + ", supplierId=" + supplierId
				+ ", categoryId=" + categoryId + ", productQty=" + productQty + ", productPrice=" + productPrice
				+ ", finalProductPrice=" + finalProductPrice + ", charges=" + charges + "]";
	}

}
