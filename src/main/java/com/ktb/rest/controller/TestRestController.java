package com.ktb.rest.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.ktb.dao.EmployeeDao;
import com.ktb.model.EmployeeModel;
import com.ktb.services.BCLinecareDAOServices;
import com.ktb.services.SendEmailServices;

@RestController
public class TestRestController {
	private static final Logger log = LoggerFactory.getLogger(TestRestController.class);
	
	@Autowired 
	SendEmailServices sendMailServices;
	
	@Autowired
	BCLinecareDAOServices daoServices;

	@Autowired
	EmployeeDao employeeDao;
	
	@GetMapping("/sendMail")
	public @ResponseBody String sendMail(){
		log.info("sendmail info");
		return new Gson().toJson(sendMailServices.sendTextEmail(null));
	}

	@GetMapping("/searchall")
	public @ResponseBody String testQuery(){
		log.info("testQuery info");
		List<EmployeeModel> x = employeeDao.loadAllEmployee();
		return new Gson().toJson(x);
	}
	
	@GetMapping("/searchbyid")
	public @ResponseBody String searchbyid(){
		log.info("searchbyid info");
		EmployeeModel x = daoServices.findEmployeeById("620670");
		return new Gson().toJson(x);
	}
	@GetMapping("/searchmapbyid")
	public @ResponseBody String searchmapbyid(){
		log.info("searchbyid info");
		Map<String, Object> x = employeeDao.findMapEmployeeById("620670");
		return new Gson().toJson(x);
	}
}
