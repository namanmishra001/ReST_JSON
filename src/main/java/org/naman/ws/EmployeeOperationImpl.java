package org.naman.ws;

import java.util.List;

import javax.ws.rs.core.Response;

import org.naman.dao.EmployeeDAO;
import org.naman.entity.Employee;

public class EmployeeOperationImpl implements EmployeeOperations {

	
	@Override
	public Response storeEmployee(Employee employee) {
		return new EmployeeDAO().storeEmployee(employee);
	}

	@Override
	public List<Employee> getAllEmployee() throws Exception {
		return new EmployeeDAO().getAllData();
	}

	@Override
	public Employee getEmployee(int eno) throws Exception {
		Employee employee = new EmployeeDAO().getEmployee(eno);
		return employee;
	}

	@Override
	public Response deleteEmployee(int eno) {
		return new EmployeeDAO().deleteEmployee(eno);
		
	}

	@Override
	public Employee updateEmployee(Integer eno, String name,Double salary) {
		return new EmployeeDAO().updateEmployee(eno, name,salary);
	}



}
