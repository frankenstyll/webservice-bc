package com.ktb.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ktb.dao.EmployeeDao;
import com.ktb.model.EmployeeModel;

@Service
public class EmployeeDaoImpl implements EmployeeDao{
    
	@Autowired
	JdbcTemplate jdbcTemplate;
 
	@Override
	public void insert(EmployeeModel emp) {
		
	}

	@Override
	public void inserBatch(List<EmployeeModel> emps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmployeeModel> loadAllEmployee() {
		String sql = " select * from employee";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
	    
	    List<EmployeeModel> result = new ArrayList<EmployeeModel>();
	    for( Map<String, Object> row : rows ){
	    	EmployeeModel emp = new EmployeeModel();
	    	emp.setEmployeeId((String)row.get("employee_id"));
	    	emp.setEmployeeEmail((String)row.get("employee_email"));
	    	emp.setEmployeeRole((String)row.get("employee_role"));
	    	emp.setEmployeeStatus((String)row.get("employee_status"));
	    	result.add(emp);
	    }
	    
	    return result;
	}

	@Override
	public EmployeeModel findEmployeeById(String empId) {
		EmployeeModel emp = null;
		try {
			String sql = " select * from employee where employee_id = ? ";
			emp = jdbcTemplate.queryForObject(sql, 
					new Object[] {empId},
					new BeanPropertyRowMapper<EmployeeModel>(EmployeeModel.class));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public Map<String,Object> findMapEmployeeById(String empId) {
		Map<String,Object> emp = null;
		try {
			String sql = " select * from employee where employee_id = ? ";
			emp = jdbcTemplate.queryForMap(sql, 
					new Object[] {empId});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return emp;
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
