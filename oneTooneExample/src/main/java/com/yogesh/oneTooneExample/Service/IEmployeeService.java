package com.yogesh.oneTooneExample.Service;

import com.yogesh.oneTooneExample.Model.Employee;

public interface IEmployeeService {
	public void save(Employee employee);

	public Employee saveAndFlush(Employee employee);

	public void delete(Employee employee);
}
