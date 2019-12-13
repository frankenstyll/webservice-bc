package com.ktb.services.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktb.constant.WebConstant;
import com.ktb.dao.EmployeeDao;
import com.ktb.dao.RegisterDao;
import com.ktb.dao.RmLineDao;
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
	
	@Autowired
	RmLineDao rmLineDao;
	
	@Override
	public EmployeeModel findEmployeeById(String empId) {
		log.info("findEmployeeById services");
		return employeeDao.findEmployeeById(empId);
	}

	@Override
	public int insertRegisterOtp(RegisterModel regis) {
		log.info("insertRegisterOtp services");
		return registerDao.insert(regis);
	}

	@Override
	public RegisterModel validateOtp(RegisterModel regis) {
		Date cu = new Date();
		
		RegisterModel va = registerDao.validateOtp(regis);
		if(null != va) {
			
			if (va.getExpire().before(cu)) {
				
				registerDao.updateStatusFlag(regis);
				va.setStatus(WebConstant.SUCCESS_CODE);
				
			}else {
				va.setStatus(WebConstant.FAIL_CODE);
				va.setMessage("Expire");
			}
			
		}else {
			return null;
		}
		
		return va;
	}

	@Override
	public void resetOtp(RegisterModel regis) {
		registerDao.resetOtp(regis);
	}

	@Override
	public int insertRmLine(String employeeId, String userId) {
		return rmLineDao.insertRmLine(employeeId, userId);
		
	}
	
}
