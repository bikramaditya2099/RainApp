package com.rain.utils;

public interface EmailTemplates {

	String _subscriptiontemplate="<html><body><table align='center' style='border: 3px solid #78D2C1;height: 464px;padding: 32px;width: 70%;margin-top: 50px;'><tr><td ><h2 style='width:97%;height:40px;padding:10px;background:#50B1CA;color:white;border-top:15px solid #A4C136;'>RAIN SUBSCRIPTION DETAILS</h2></td></tr><tr><td><h4 style='text-align:center;font-size:15px;font-family:Verdana;color:#085E2F;'>You are successfully subscribed with Rain. Please use the below payment id to register an application.</h4></td></tr><tr><td><h5 style='text-align:center;height:35px;border:3px solid #DFC0F5;border-style:dashed;padding:20px;font-size:20px;color:#8D8E8F;'>_PAYMENTID_</h5></td></tr></table><br><br><br> <h5 style='color:#FFB1B1'>* This is a system generated email. Please do not reply and if you have any query , please leave a query message in our query board at www.phonebook.com</h5></body></html>";
	String _applicationRegistertemplate="<html><body><table align='center' style='border: 3px solid #78D2C1;height: 464px;padding: 32px;width: 70%;margin-top: 50px;'><tr><td ><h2 style='width:97%;height:40px;padding:10px;background:#50B1CA;color:white;border-top:15px solid #A4C136;'>RAIN APPLICATION DETAILS</h2></td></tr><tr><td><h4 style='text-align:center;font-size:15px;font-family:Verdana;color:#085E2F;'>You are successfully register the application <b>_APPNAME_</b>. Please use the below application id to register users.</h4></td></tr><tr><td><h5 style='text-align:center;height:35px;border:3px solid #DFC0F5;border-style:dashed;padding:20px;font-size:20px;color:#8D8E8F;'>_APPID_</h5></td></tr></table><br><br><br> <h5 style='color:#FFB1B1'>* This is a system generated email. Please do not reply and if you have any query , please leave a query message in our query board at www.phonebook.com</h5></body></html>";
}