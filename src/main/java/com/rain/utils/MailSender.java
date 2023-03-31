package com.rain.utils;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MailSender {
	
	
	 @Value("${from}")
	private  String userName;
	 @Value("${pass}")
	private  String password;
	 
	 

	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public  void send(String emailId,String content,String subject)
	{
		
		 
		
		  
		  String to=emailId;
		  
		  String message=content;
		  String host="smtp.gmail.com";
		  Properties properties=new Properties();
		//  properties.put("mail.debug", "true"); 
		  properties.put("mail.smtp.auth", "true"); 
		  properties.put("mail.smtp.starttls.enable","true"); 
		  properties.put("mail.smtp.EnableSSL.enable","true");
		  properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
		  properties.setProperty("mail.smtp.socketFactory.fallback", "false"); 
		  properties.setProperty("mail.smtp.port", "465"); 
		  properties.setProperty("mail.smtp.socketFactory.port", "465");

		   properties.put("mail.smtp.starttls.required","true");
		  properties.put("mail.smtp.host",host);
		  properties.put("mail.smtp.port","587");
		  
		  
		   Session session = Session.getInstance(properties,
		         new javax.mail.Authenticator() {
		            protected PasswordAuthentication getPasswordAuthentication() {
		               return new PasswordAuthentication(userName, password);
		            }
		         });
		  try
		  {
		   Message mesg=new MimeMessage(session);
		   mesg.setFrom(new InternetAddress(userName));
		   mesg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		   mesg.setSubject(subject);
		   mesg.setContent(message,"text/html");
		   //mesg.setText(message);
		   Transport.send(mesg);
		  
		    
		  }
		  catch(AuthenticationFailedException ex){
			  System.out.println("something went wrong while sending email "+ex);
		  }
		  catch(Exception e)
		  {
		   e.printStackTrace();
		  }
	}
	

}
