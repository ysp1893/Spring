package com.yogesh.SpringBootUsingJPA.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yogesh.SpringBootUsingJPA.Model.Employee;
import com.yogesh.SpringBootUsingJPA.Repository.IEmployeeRepository;
import com.yogesh.SpringBootUsingJPA.Service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	IEmployeeRepository iEmployeeRepository;

	@Transactional
	@Override
	public void save(Employee employee) {
		iEmployeeRepository.save(employee);
	}

	@Transactional
	@Override
	public Employee saveAndFlush(Employee employee) {
		return iEmployeeRepository.saveAndFlush(employee);
	}

	@Transactional
	@Override
	public void delete(Employee employee) {
		iEmployeeRepository.delete(employee);
	}

}
