package com.sbwithdb.exceptions;

public class ProductNotExistException extends RuntimeException{

	public ProductNotExistException(String msg){
		super(msg);
	}
}
