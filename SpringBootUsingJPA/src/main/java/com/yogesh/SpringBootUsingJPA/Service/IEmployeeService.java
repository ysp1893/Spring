package com.yogesh.SpringBootUsingJPA.Service;

import com.yogesh.SpringBootUsingJPA.Model.Employee;

public interface IEmployeeService {
	public void save(Employee employee);

	public Employee saveAndFlush(Employee employee);

	public void delete(Employee employee);
}
