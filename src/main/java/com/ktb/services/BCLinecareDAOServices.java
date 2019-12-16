package com.ktb.services;

import com.ktb.model.EmployeeModel;
import com.ktb.model.RegisterModel;
import com.ktb.model.RmLineModel;

public interface BCLinecareDAOServices {

	EmployeeModel findEmployeeById(String empId);
	
	int insertRegisterOtp(RegisterModel regis);
	
	RegisterModel validateOtp(RegisterModel regis);
	
	int insertRmLine( String employeeId ,String userId);
	
	int resetOtp(RegisterModel oldData, String newOtp, String newRefNumber);
	
	RmLineModel searchRmLine(String userId);
}
