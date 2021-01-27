package com.yogesh.SpringBootUsingJPA.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogesh.SpringBootUsingJPA.DTO.EmployeeDTO;
import com.yogesh.SpringBootUsingJPA.Model.Employee;

public interface IEmployeeRepository extends JpaRepository<Employee, String> {
	public List<Employee> findAll();

	public Optional<Employee> findById(String id);

	public Optional<Employee> findByUsername(String name);

	@Query("Select new com.yogesh.SpringBootUsingJPA.DTO.EmployeeDTO(e.panid, e.username) from Employee e where e.id=?1")
	public Optional<EmployeeDTO> retriveEmployeePanIdUsername(String id);
	
//	@Query("Select * com.yogesh.SpringBootUsingJPA.DTO.EmployeeDTO() from Employee e")
	@Query("From Employee e")
	public Page<Employee> findAllPage(Pageable pageable);
}
