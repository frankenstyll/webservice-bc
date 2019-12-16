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
	public int insert(RegisterModel regis) {
		int count = 0;
		try {
			String sql = " 	insert into register (otp, ref_number, created, expire, employee_id , status) " + 
					"	values(?,?, now() , (now() + time '00:05') , ? , 'N')";
			
			count = jdbcTemplate.update(sql, new Object[] {
				regis.getOtp(), regis.getRefNumber(), regis.getEmployeeId()	
			});
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public RegisterModel validateOtp(RegisterModel regis) {
		RegisterModel reg = null;
		try {
			String sql = " 	select * from register " + 
					"	where employee_id = ? " + 
					"	and otp = ? " + 
					"	and ref_number = ? " + 
					"	and status = 'N' " ;
			reg = jdbcTemplate.queryForObject(sql, 
					new Object[] {regis.getEmployeeId() , regis.getOtp(), regis.getRefNumber() },
					new BeanPropertyRowMapper<RegisterModel>(RegisterModel.class));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reg;
	}
	
	@Override
	public int updateStatusFlag(RegisterModel regis) {
		int count = 0;
		try {
			String sql = " 	update register set status = ? " + 
					" where employee_id = ? " +
					" otp = ? and ref_number = ? ";
			count = jdbcTemplate.update(sql, new Object[] {
				regis.getStatus(), regis.getEmployeeId(), regis.getOtp(), regis.getRefNumber() 
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int resetOtp(RegisterModel regis) {
		int count = 0;
		try {
			String sql = " 	update register set expire = (now() + time '00:05') , otp = ? , ref_number = ? , status = 'N' " + 
					"	where employee_id = ? and otp = ? and ref_number = ? ";
			
			count = jdbcTemplate.update(sql, new Object[] {
				regis.getOtp(), regis.getRefNumber(), regis.getEmployeeId() , regis.getOtp(), regis.getRefNumber()	
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return count;
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
