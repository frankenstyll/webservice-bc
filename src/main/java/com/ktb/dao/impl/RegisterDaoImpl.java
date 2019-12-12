package com.ktb.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ktb.dao.RegisterDao;
import com.ktb.model.RegisterModel;

@Service
public class RegisterDaoImpl implements RegisterDao{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void insert(RegisterModel regis) {
		String sql = " 	insert into register (otp, ref_number, created, expire, employee_id) " + 
				"	values(?,?, now() , (now() + time '00:05') , ?)";
		
		jdbcTemplate.update(sql, new Object[] {
			regis.getOtp(), regis.getRefNumber(), regis.getEmployeeId()	
		});
		
	}
	
	@Override
	public RegisterModel validateOtp(RegisterModel regis) {
		String sql = " 	select * from register " + 
				"	where employee_id = ? " + 
				"	and otp = ? " + 
				"	and ref_number = ? " + 
				"	and expire > now() ";
		RegisterModel emp = jdbcTemplate.queryForObject(sql, 
				new Object[] {regis.getEmployeeId() , regis.getOtp(), regis.getRefNumber() },
				new BeanPropertyRowMapper<RegisterModel>(RegisterModel.class));
		return emp;
	}

	@Override
	public void resetOtp(RegisterModel regis) {
		
		String sql = " 	update register set expire = (now() + time '00:05') , otp = ? , ref_number = ? " + 
				"	where employee_id = ? ";
		
		jdbcTemplate.update(sql, new Object[] {
			regis.getOtp(), regis.getRefNumber(), regis.getEmployeeId()	
		});
	}
	
	@Override
	public void inserBatch(List<RegisterModel> emps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<RegisterModel> loadAllRegister() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegisterModel findRegisterById(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findNameById(String empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalNumberEmployee() {
		// TODO Auto-generated method stub
		return 0;
	}



	
}
