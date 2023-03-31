package com.rain.utils;

import java.lang.reflect.Field;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncoderAndDecoderUtils {
	
	public static String delim="@@@@--@@@@";

	private static BasicTextEncryptor textEncryptor;
	
	private  String password;

	public String getPassword() {
		return password;
	}

	static {
		init();
	}

	public static void init() {
		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("ligF4j1mbc4aPh5@0LPgvdpjdksaj$fhsd8=");

	}

	public static String encrypt(String value) {
		if (textEncryptor == null)
			init();
		return textEncryptor.encrypt(value);

	}

	public static String decrypt(String value) {
		if (textEncryptor == null)
			init();
		return textEncryptor.decrypt(value);

	}
	
	

}