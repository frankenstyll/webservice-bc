package com.ktb.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ktb.dao.services.BCLinecareDAOServices;
import com.ktb.model.EmployeeModel;
import com.ktb.model.Register;
import com.ktb.services.SendEmailServices;
import com.ktb.utils.StringUtils;

@RestController
public class RegisterLineController {

	private static final Logger log = LoggerFactory.getLogger(RegisterLineController.class);
	
	@Autowired 
	SendEmailServices sendMailServices;
	
	@Autowired
	BCLinecareDAOServices daoServices;
	
	@GetMapping("/sendMail")
	public @ResponseBody String sendMail(){
		log.info("sendmail info");
		return new Gson().toJson(sendMailServices.sendTextEmail(null));
	}

	@GetMapping("/testQuery")
	public @ResponseBody String testQuery(){
		log.info("testQuery info");
		
		List<EmployeeModel> x = daoServices.searchEmployee("620670");
		
		return new Gson().toJson(x);
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
			String OTP = StringUtils.generateOTP(6);
			
			//3.generate ref number
			String refNumber = StringUtils.generateRandomStringByUUIDNoDash();
			
			//4.send otp number
			SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nontapap.th@gmail.com");
            message.setTo("nontapap.th@gmail.com");
            message.setSubject("RM Register OTP");
            message.setText("รหัส OTP ของคุณคือ " + OTP + " (Ref : "+ refNumber +")");
            sendMailServices.sendTextEmail(message);
			
            //5.insert to DATABASE
            
			//6.response result
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
