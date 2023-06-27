package com.sbwithdb.model;

public class Charges {

	private double gstCharge;
	private double deliveryCharge;
	private double discountAmount;

	public Charges() {
		// TODO Auto-generated constructor stub
	}

	public Charges(double gstCharge, double deliveryCharge, double discountAmount) {
		super();
		this.gstCharge = gstCharge;
		this.deliveryCharge = deliveryCharge;
		this.discountAmount = discountAmount;
	}

	public double getGstCharge() {
		return gstCharge;
	}

	public void setGstCharge(double gstCharge) {
		this.gstCharge = gstCharge;
	}

	public double getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Override
	public String toString() {
		return "Charges [gstCharge=" + gstCharge + ", deliveryCharge=" + deliveryCharge + ", discountAmount="
				+ discountAmount + "]";
	}

}
