package org.naman.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.naman.entity.Employee;

public class EmployeeDAO {
	Connection connection = null;
	PreparedStatement prepareStatement = null;

	private final String GET_ALL_EMPLOYEE_QUERY = "select * from employee";
	private final String GET_EMPLOYEE_BY_ID_QUERY = "select * from employee where eno=?";
	private final String STORE_EMPLOYEE_QUERY = "insert into employee values(?,?,?)";
	private final String UPDATE_EMPLOYEE_QUERY = "update employee set (name,salary) where eno=?";
	private final String DELETE_EMPLOYEE_QUERY = "delete from employee where eno= ?";

	/*
	 * This getAllData() is used to retrieve all employee record from database
	 * 
	 */
	public List<Employee> getAllData() {
		ArrayList<Employee> empList = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "inetsolv", "system");
			prepareStatement = connection.prepareStatement(GET_ALL_EMPLOYEE_QUERY);
			ResultSet resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setEno(resultSet.getInt(1));
				employee.setName(resultSet.getString(2));
				employee.setSalary(resultSet.getDouble(3));
				empList.add(employee);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return empList;

	}

	/*
	 * This getEmployee() is used to retrieve single employee record based on
	 * {eno} from database
	 * 
	 */
	public Employee getEmployee(int eno) {
		Employee employee = new Employee();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "inetsolv", "system");
			prepareStatement = connection.prepareStatement(GET_EMPLOYEE_BY_ID_QUERY);
			prepareStatement.setInt(1, eno);
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				employee.setEno(resultSet.getInt(1));
				employee.setName(resultSet.getString(2));
				employee.setSalary(resultSet.getDouble(3));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employee;

	}

	/*
	 * This storeData() is used to store single employee data by supplying xml
	 * data as an input
	 * 
	 */

	public Response storeEmployee(Employee employee) {
		int status = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "inetsolv", "system");
			prepareStatement = connection.prepareStatement(STORE_EMPLOYEE_QUERY);
			prepareStatement.setInt(1, employee.getEno());
			prepareStatement.setString(2, employee.getName());
			prepareStatement.setDouble(3, employee.getSalary());
			status = prepareStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (status == 1) {
			return Response.status(200).entity("succesfully stored").build();
		} else {
			return Response.status(406).build();
		}
	}

	/*
	 * This deleteEmployee() is used to delete single employee data based on
	 * {eno} from database
	 * 
	 */
	public Response deleteEmployee(int eno) {
		int status = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "inetsolv", "system");
			prepareStatement = connection.prepareStatement(DELETE_EMPLOYEE_QUERY);
			prepareStatement.setInt(1, eno);
			status = prepareStatement.executeUpdate();
			System.out.println("status returned " + status);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (status == 1) {
			return Response.status(200).entity("succesfully deleted").build();
		} else {
			return Response.status(406).build();
		}
	}

	public Employee updateEmployee(int eno, String name, Double salary) {
		// just for test purpose of query param
		Employee employee = new Employee();
		employee.setEno(eno);
		employee.setName(name);
		employee.setSalary(salary);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "inetsolv", "system");
			prepareStatement = connection.prepareStatement(UPDATE_EMPLOYEE_QUERY);
			prepareStatement.setInt(1, eno);
			prepareStatement.setString(2, name);
			prepareStatement.setDouble(3, salary);
			//System.out.println();
			prepareStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				prepareStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return employee;
	}
}
