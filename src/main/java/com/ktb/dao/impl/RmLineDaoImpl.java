package com.ktb.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ktb.dao.RmLineDao;
import com.ktb.model.RmLineModel;

@Service
public class RmLineDaoImpl implements RmLineDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public int insertRmLine(String employeeId, String userId) {
		int count = 0;
		try {
			String sql = " 	insert into rm_line (employee_id, line_id , created) " + 
					"	values(?,?, now())";
			count = jdbcTemplate.update(sql, new Object[] { employeeId, userId });
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public RmLineModel searchRmLine(String userId) {
		RmLineModel rm = null;
		try {
			String sql = " select * from rm_line where line_id = ? " ;
			rm = jdbcTemplate.queryForObject(sql, 
					new Object[] {userId },
					new BeanPropertyRowMapper<RmLineModel>(RmLineModel.class));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rm;
	}

}
