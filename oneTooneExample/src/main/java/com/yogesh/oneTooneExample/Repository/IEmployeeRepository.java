package com.yogesh.oneTooneExample.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogesh.oneTooneExample.DTO.EmployeeDTO;
import com.yogesh.oneTooneExample.Model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, String> {
	
//	@Query("select a from Employee a left join fetch a.address")
	public List<Employee> findAll();

	public Optional<Employee> findById(String id);

	public Optional<Employee> findByUsername(String name);

	@Query("Select new com.yogesh.oneTooneExample.DTO.EmployeeDTO(e.id, e.username) from Employee e")
	public List<EmployeeDTO> findAllByDTO();
}
