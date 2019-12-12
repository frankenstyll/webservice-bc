package com.ktb.services;

import com.ktb.model.EmployeeModel;
import com.ktb.model.RegisterModel;

public interface BCLinecareDAOServices {

	EmployeeModel findEmployeeById(String empId);
	
	void insertRegisterOtp(RegisterModel regis);
	
	RegisterModel validateOtp(RegisterModel regis);
	
	void resetOtp(RegisterModel regis);
}
