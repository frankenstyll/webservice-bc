package com.ktb.dao;

import java.util.List;

import com.ktb.model.EmployeeModel;

public interface EmployeeDao {
	  void insert(EmployeeModel emp);
	  void inserBatch(List<EmployeeModel> emps);
	  List<EmployeeModel> loadAllEmployee();
	  EmployeeModel findEmployeeById(String empId);
	  String findNameById(String empId);
	  int getTotalNumberEmployee();
}
