package com.microservice.UserServiceexception;

public class ResourceNotFaundException extends RuntimeException {
	String resourceName;
	String fieldName;
	long fieldValue;

	public ResourceNotFaundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
