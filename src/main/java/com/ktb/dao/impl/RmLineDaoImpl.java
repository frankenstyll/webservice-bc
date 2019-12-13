package com.ktb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ktb.dao.RmLineDao;
import com.ktb.model.RegisterModel;
import com.ktb.model.RmLineModel;

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

	@Override
	public RmLineModel searchRmLine(String userId) {
		String sql = " select * from rm_line where line_id = ? " ;
		RmLineModel emp = jdbcTemplate.queryForObject(sql, 
				new Object[] {userId },
				new BeanPropertyRowMapper<RmLineModel>(RmLineModel.class));
		return emp;
	}

}
