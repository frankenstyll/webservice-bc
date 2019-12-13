package com.ktb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ktb.dao.RmLineDao;

@Service
public class RmLineDaoImpl implements RmLineDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int insertRmLine(String userId, String employeeId) {
		String sql = " 	insert into rm_line (employee_id, line_id , created) " + 
				"	values(?,?, now())";
		
		return jdbcTemplate.update(sql, new Object[] {
			employeeId, userId	
		});
	}

}
