package com.rain.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class RainDateFormat extends SimpleDateFormat {

	public RainDateFormat() {
			
		super("dd-mm-yyyy");
	}
	
}
