package com.ktb.rest.controller;

import java.util.Date;
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
import com.ktb.model.RmLineModel;
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

		String step = "";
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			
			//TODO
			//1.Search and validate user from LDAP
			step = "find Employee -> ";
			EmployeeModel emp = bcLinecareDao.findEmployeeById(register.getEmployeeId());
			if (null == emp) {
				throw new Exception("employee is not found!");
			}
			
			//2.generate otp
			step += " Generate OTP -> ";
			String OTP = StringUtils.generateOTP(6);
			
			//3.generate ref number
			step += "Generate RefNumber -> ";
			String refNumber = StringUtils.generateRandomStringByUUIDNoDash();
			
			//4.send otp number
			step += "Create Email message -> ";
			SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nontapap.th@gmail.com");
            message.setTo(emp.getEmployeeEmail());
            message.setSubject("RM Register OTP");
            message.setText("รหัส OTP ของคุณคือ " + OTP + " (Ref : "+ refNumber +")");
            
            step += "Send Email to "+emp.getEmployeeEmail()+" -> ";
            sendMailServices.sendTextEmail(message);
			
            //5.insert otp to DATABASE
			register.setEmail(emp.getEmployeeEmail());
			register.setEmployeeId(emp.getEmployeeId());
			register.setOtp(OTP);
			register.setRefNumber(refNumber);
			step += "Insert registerOTP -> ";
            bcLinecareDao.insertRegisterOtp(register);
            
			//6.response result
            step += "Set Response; ";
			m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
			m.put("ref_number", refNumber);
			m.put("step", step);
            
		  } catch (Exception e) {
			  log.error(e.getMessage());
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
		  } finally {
			resp = new Gson().toJson(m);
		  }
		
		return resp;
	}
	
	@GetMapping("/validateOTP")
	public @ResponseBody String validateOTP(@ModelAttribute RegisterModel register) {
		log.info("validateOTP info");
		
		String step = "";
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		
		try {
			
			log.info("search otp");
			step += "search otp -> ";
			RegisterModel resultValidate = bcLinecareDao.validateOtp(register);
			
			if( null != resultValidate) {
				
				/**
				 * test
				 */
				Date v = new Date();
				m.put("currentTime", v);
				m.put("expireTime", resultValidate.getExpire());
				m.put("Compare", v.before(resultValidate.getExpire()));
				
				if(WebConstant.SUCCESS_CODE.equals(resultValidate.getStatus())) {
					
					log.info("insert rm line");
					step += "insert rm line -> ";
					int tiger = bcLinecareDao.insertRmLine(register.getEmployeeId(), register.getUserId());
					if(1 == tiger) {
						log.info("response success");
						step += "response success ";
						m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
						
					}else {
						log.info("response insert rm line fail");
						step += "response insert rm line fail";
						m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
						m.put(WebConstant.MESSAGE_TEXT, "Cannot save Line User. please try again or contact administrator");
					}
					
				}else {
					log.info("response otp is expire");
					step += "response otp is expire";
					m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
					m.put(WebConstant.MESSAGE_TEXT, "OTP is expired");
				}
				
			}else {
				log.info("response otp is not found");
				step += "response otp is not found";
				m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
				m.put(WebConstant.MESSAGE_TEXT, "OTP is invalid. please try again");
			}
			
		  } catch (Exception e) {
			log.error(e.getMessage());
			step += "Exception " + e.getMessage();
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
		  } finally {
			  m.put("step", step);
			  resp = new Gson().toJson(m);
		  }
		
		return resp;
	}
	
	@GetMapping("/resetOTP")
	public @ResponseBody String resetOTP(@ModelAttribute RegisterModel oldData) {
		log.info("resetOTP info");
		
		String step = "";
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			//1.generate otp
			step += "generate otp - ";
			String OTP = StringUtils.generateOTP(6);
			
			//2.generate ref number
			step += "generate ref number - ";
			String refNumber = StringUtils.generateRandomStringByUUIDNoDash();
			
			//3.update otp
			step += "update otp - ";
			int count = bcLinecareDao.resetOtp(oldData, OTP, refNumber);
			if(1 == count) {
				//4.response result
				step += "set response";
				m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
				m.put("ref_number", refNumber);
			}else {
				//5.response result
				step += "set response - ";
				m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
				m.put(WebConstant.MESSAGE_TEXT, "Can not reset OTP");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			step += "Exception " + e.getMessage();
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
			
		} finally {
			m.put("step", step);
			resp = new Gson().toJson(m);
		}
		
		return resp;
	}
	
	@GetMapping("/isRegistered")
	public @ResponseBody String isRegistered(@ModelAttribute RegisterModel register) {
		log.info("isRegistered info");
		
		String resp = "";
		Map<String,Object> m = new HashMap<String,Object>();
		try {
			
			if(StringUtils.isNullOrEmpty(register.getEmployeeId()) && StringUtils.isNullOrEmpty(register.getUserId())) {
				throw new Exception("Employee ID and User ID is empty!");
			}
			
			RmLineModel rmModel = bcLinecareDao.searchRmLine(register.getUserId() , register.getEmployeeId());
			if(null != rmModel) {
				m.put(WebConstant.MESSAGE_TEXT, "REGISTERED");
			}else {
				m.put(WebConstant.MESSAGE_TEXT, "NEW");
			}
			m.put(WebConstant.STATUS_TEXT, WebConstant.SUCCESS_CODE);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			m.put(WebConstant.STATUS_TEXT, WebConstant.FAIL_CODE);
			m.put(WebConstant.MESSAGE_TEXT, e.getMessage());
		} finally {
			resp = new Gson().toJson(m);
		}
		return resp;
	}
}
