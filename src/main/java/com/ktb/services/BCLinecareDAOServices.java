package com.ktb.services;

import java.util.List;

import com.ktb.model.EmployeeModel;

public interface BCLinecareDAOServices {

	List<EmployeeModel> loadAllEmployee();
	EmployeeModel findEmployeeById(String empId);
}
