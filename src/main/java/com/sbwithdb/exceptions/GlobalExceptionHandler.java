package com.sbwithdb.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	public Map<String, String> map = new HashMap<>();

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

		BindingResult bindingResult = ex.getBindingResult();

		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

//		Map<String, String> map = new HashMap<>();

		for (FieldError fieldError : fieldErrors) {
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return map;
	}
	
	
	@ExceptionHandler(ProductAlreadyExists.class)
	public ResponseEntity<String> productAlreadyExists(ProductAlreadyExists ex) {
		
		return new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
//		return ResponseEntity.ok(ex.getMessage());
	}
	
	@ExceptionHandler(ProductNotExistException.class)
	public String ProductNotExists(ProductNotExistException ex) {
		
		return ex.getMessage();
//		return ResponseEntity.ok(ex.getMessage());
	}

}
