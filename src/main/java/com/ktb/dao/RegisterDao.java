package com.ktb.dao;

import java.util.List;

import com.ktb.model.RegisterModel;

public interface RegisterDao {

	  int insert(RegisterModel regis);
	  
	  void inserBatch(List<RegisterModel> emps);
	  
	  List<RegisterModel> loadAllRegister();
	  
	  RegisterModel findRegisterById(String empId);
	  
	  String findNameById(String empId);
	  
	  int getTotalNumberEmployee();
	  
	  RegisterModel validateOtp(RegisterModel regis);

	  int resetOtp(RegisterModel oldRegister, String newOtp ,String newRefNumber);
	  
	  int updateStatusFlag(RegisterModel regis) ;
}
