package com.ktb.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ktb.constant.WebConstant;
import com.ktb.model.EmployeeModel;
import com.ktb.model.RegisterModel;
import com.ktb.services.BCLinecareDAOServices;
import com.ktb.services.SendEmailServices;
import com.ktb.utils.StringUtils;


@RestController
public class RegisterLineController {

	private static final Logger log = LoggerFactory.getLogger(RegisterLineController.class);
	
	@Autowired 
	SendEmailServices sendMailServices;
	
	@Autowired
	BCLinecareDAOServices bcLinecareDao;
	
	@GetMapping("/registerRequestOTP")
	public @ResponseBody String registerRequestOTP(@ModelAttribute RegisterModel register) {
		log.info("registerRequestOTP info");
		
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			
			//TODO
			//1.Search and validate user from LDAP
			EmployeeModel emp = bcLinecareDao.findEmployeeById(register.getEmployeeId());
			if (null == emp) {
				throw new Exception("employee is not found!");
			}
			
			//2.generate otp
			String OTP = StringUtils.generateOTP(6);
			
			//3.generate ref number
			String refNumber = StringUtils.generateRandomStringByUUIDNoDash();
			
			//4.send otp number
			SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nontapap.th@gmail.com");
            message.setTo(emp.getEmployeeEmail());
            message.setSubject("RM Register OTP");
            message.setText("รหัส OTP ของคุณคือ " + OTP + " (Ref : "+ refNumber +")");
            sendMailServices.sendTextEmail(message);
			
            //5.insert otp to DATABASE
			register.setEmail(emp.getEmployeeEmail());
			register.setEmployeeId(emp.getEmployeeId());
			register.setOtp(OTP);
			register.setRefNumber(refNumber);
            bcLinecareDao.insertRegisterOtp(register);
            
			//6.response result
			m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
			m.put("ref_number", refNumber);
            
			resp = new Gson().toJson(m);
			
		  } catch (Exception e) {
			e.printStackTrace();
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
		  }
		
		return resp;
	}
	
	@GetMapping("/validateOTP")
	public @ResponseBody String validateOTP(@ModelAttribute RegisterModel register) {
		log.info("validateOTP info");
		
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			
			RegisterModel resultValidate = bcLinecareDao.validateOtp(register);
			if( null == resultValidate) {
				m.put(WebConstant.MESSAGE_TEXT, "OTP is expire");
				m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			}else {
				m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
			}
			//.response result
			resp = new Gson().toJson(m);
			
		  } catch (Exception e) {
			e.printStackTrace();
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
		  }
		
		return resp;
	}
	
	@GetMapping("/resetOTP")
	public @ResponseBody String resetOTP(@ModelAttribute RegisterModel register) {
		log.info("resetOTP info");
		
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			
			bcLinecareDao.resetOtp(register);
			
			//.response result
			m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
			resp = new Gson().toJson(m);
			
		} catch (Exception e) {
			e.printStackTrace();
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
		}
		
		return resp;
	}
}
