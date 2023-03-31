package com.rain.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component

public class AuthorizationBean {
	   @Value("${name}")
	private String name;
	   @Value("${password}")
	private String password;
	   @Value("${cert}")
	 private String certFile;
	
	   
	public X509Certificate getCertificate()
	{
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(certFile);
		X509Certificate cert=null;
		try {
			
			System.out.println(certFile);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
		
			
			 cert=(X509Certificate) cf.generateCertificate(input);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cert;
		
	}

	public String getCertFile() {
		return certFile;
	}
	public void setCertFile(String certFile) {
		this.certFile = certFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;  
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
