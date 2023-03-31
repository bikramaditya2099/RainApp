package com.rain.utils;

public class RainException extends Exception{
	
	private int code;
	private String message;
	private String status;
	
	private Object value;
	
	
	
	public String getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getValue() {
		return value;
	}

	public RainException(RainExceptionEnum enum1,Object value)
	{
		this.code=enum1.getCode();
		this.message=enum1.getMessage();
		this.value=value;
		this.status=enum1.getStatus();
	}
	
	public RainException(RainExceptionEnum enum1)
	{
		this.code=enum1.getCode();
		this.message=enum1.getMessage();
		this.status=enum1.getStatus();
	}

}
