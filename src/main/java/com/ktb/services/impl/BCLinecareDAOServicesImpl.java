package com.ktb.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktb.dao.EmployeeDao;
import com.ktb.dao.RegisterDao;
import com.ktb.model.EmployeeModel;
import com.ktb.model.RegisterModel;
import com.ktb.services.BCLinecareDAOServices;

@Service
public class BCLinecareDAOServicesImpl  implements BCLinecareDAOServices{

	private static final Logger log = LoggerFactory.getLogger(BCLinecareDAOServicesImpl.class);
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Autowired
	RegisterDao registerDao;
	
	@Override
	public EmployeeModel findEmployeeById(String empId) {
		log.info("findEmployeeById services");
		return employeeDao.findEmployeeById(empId);
	}

	@Override
	public void insertRegisterOtp(RegisterModel regis) {
		log.info("insertRegisterOtp services");
		registerDao.insert(regis);
	}

	@Override
	public RegisterModel validateOtp(RegisterModel regis) {
		return registerDao.validateOtp(regis);
	}

	@Override
	public void resetOtp(RegisterModel regis) {
		registerDao.resetOtp(regis);
	}
	
}
