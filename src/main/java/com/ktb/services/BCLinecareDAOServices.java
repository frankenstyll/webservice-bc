package com.ktb.services;

import java.util.List;
import java.util.Map;

import com.ktb.model.EmployeeModel;

public interface BCLinecareDAOServices {

	List<EmployeeModel> loadAllEmployee();
	EmployeeModel findEmployeeById(String empId);
	Map<String,Object> findMapEmployeeById(String empId);
}
