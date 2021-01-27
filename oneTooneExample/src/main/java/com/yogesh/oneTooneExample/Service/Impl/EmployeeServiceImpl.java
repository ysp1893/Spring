package com.yogesh.oneTooneExample.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import com.yogesh.oneTooneExample.Model.Employee;
import com.yogesh.oneTooneExample.Repository.IEmployeeRepository;
import com.yogesh.oneTooneExample.Service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	IEmployeeRepository iEmployeeRepository;

	@Transient
	@Override
	public void save(Employee employee) {
		iEmployeeRepository.save(employee);
	}

	@Transient
	@Override
	public Employee saveAndFlush(Employee employee) {
		return iEmployeeRepository.saveAndFlush(employee);
	}

	@Transient
	@Override
	public void delete(Employee employee) {
		iEmployeeRepository.delete(employee);
	}

}
