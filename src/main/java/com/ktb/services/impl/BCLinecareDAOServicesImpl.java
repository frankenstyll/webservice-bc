package com.ktb.services.impl;

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
import com.ktb.model.RmLineModel;
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
			
			if (cu.before(va.getExpire())) {
				
				regis.setStatus(WebConstant.STATUS_ACTIVE);
				registerDao.updateStatusFlag(regis);
				
				va.setStatus(WebConstant.SUCCESS_CODE);
				
			}else {
				va.setStatus(WebConstant.FAIL_CODE);
				va.setMessage("Expire");
			}
		}
		
		return va;
	}

	@Override
	public int resetOtp(RegisterModel oldData , String newOtp , String newRefNumber) {
		return registerDao.resetOtp(oldData, newOtp, newRefNumber);
	}

	@Override
	public int insertRmLine(String employeeId, String userId) {
		return rmLineDao.insertRmLine(employeeId, userId);
	}

	@Override
	public RmLineModel searchRmLine(String userId , String employeeID) {
		return rmLineDao.searchRmLine(userId, employeeID);
	}
	
}
