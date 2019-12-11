package com.ktb.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktb.dao.EmployeeDao;
import com.ktb.model.EmployeeModel;
import com.ktb.services.BCLinecareDAOServices;

@Service
public class BCLinecareDAOServicesImpl  implements BCLinecareDAOServices{

	private static final Logger log = LoggerFactory.getLogger(BCLinecareDAOServicesImpl.class);
	
	@Autowired
	EmployeeDao employeeDao;
	
	@Override
	public List<EmployeeModel> loadAllEmployee() {
		log.info("loadAllEmployee services");
		return employeeDao.loadAllEmployee();
	}

	@Override
	public EmployeeModel findEmployeeById(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

}
