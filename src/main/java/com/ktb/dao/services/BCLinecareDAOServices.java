package com.ktb.dao.services;

import java.util.List;

import com.ktb.model.EmployeeModel;

public interface BCLinecareDAOServices {

	List<EmployeeModel> searchEmployee(String employeeId);
}
