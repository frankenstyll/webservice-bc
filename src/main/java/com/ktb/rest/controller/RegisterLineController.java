package com.ktb.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ktb.model.Register;
import com.ktb.services.SendEmailServices;

@RestController
public class RegisterLineController {

	private static final Logger log = LoggerFactory.getLogger(RegisterLineController.class);
	
	@Autowired 
	SendEmailServices sendMailServices;
	
	@GetMapping("/sendMail")
	public @ResponseBody String sendMail(){
		log.info("sendmail info");
		return new Gson().toJson(sendMailServices.sendTextEmail(null));
	}

	@GetMapping("/validateRequestOTP")
	public @ResponseBody String validateRequestOTP(@ModelAttribute Register register) {
		log.info("validateRequestOTP info");
		
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			
			//TODO
			//1.Search and validate user from LDAP
			
			
			//2.generate otp
			String OTP = "123456";
			
			//3.generate ref number
			String refNumber = "1234567890";
			
			//4.send otp number
			SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nontapap.th@gmail.com");
            message.setTo("nontapap.th@gmail.com");
            message.setSubject("RM Register OTP");
            message.setText("<html>รหัส OTP ของคุณคือ <font color='red'>" + OTP + "</font> (Ref : "+ refNumber +")</html>");
            sendMailServices.sendTextEmail(message);
			
			//5.response result
			m.put("status", "0");
            
			resp = new Gson().toJson(m);
			
		  } catch (Exception e) {
			e.printStackTrace();
			m.put("status", "1");
			m.put("message", e.getMessage());
		  }
		
		return resp;
	}
	
	private static void getEmployees(){
	    final String uri = "http://localhost:8080/springrestexample/employees.xml";
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	    System.out.println(result);
	}
}
