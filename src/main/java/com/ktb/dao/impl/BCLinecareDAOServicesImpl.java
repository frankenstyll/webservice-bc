package com.ktb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.ktb.dao.services.BCLinecareDAOServices;
import com.ktb.model.EmployeeModel;

@Component
@Service
public class BCLinecareDAOServicesImpl implements BCLinecareDAOServices{

	private static final Logger log = LoggerFactory.getLogger(BCLinecareDAOServicesImpl.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<EmployeeModel> searchEmployee(String employeeId) {
		log.info("[services] searchEmployee " + employeeId);
		String sql = " select * from employee ";
		List<EmployeeModel> empModel = jdbcTemplate.queryForList(sql, EmployeeModel.class);
		return empModel;
	}

}
