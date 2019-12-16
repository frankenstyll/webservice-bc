package com.ktb.dao;

import com.ktb.model.RmLineModel;

public interface RmLineDao {

	int insertRmLine(String userId, String employeeId);
	
	RmLineModel searchRmLine(String userId ,String employeeId);
}
