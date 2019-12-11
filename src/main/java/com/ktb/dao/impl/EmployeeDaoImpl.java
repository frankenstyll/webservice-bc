package com.ktb.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.ktb.dao.EmployeeDao;
import com.ktb.model.EmployeeModel;

@Repository
public class EmployeeDaoImpl extends JdbcDaoSupport implements EmployeeDao{
    
	@Autowired 
    DataSource dataSource;
 
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    
	@Override
	public void insert(EmployeeModel emp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inserBatch(List<EmployeeModel> emps) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmployeeModel> loadAllEmployee() {
		String sql = "select * from employee";
	    List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
	    
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
