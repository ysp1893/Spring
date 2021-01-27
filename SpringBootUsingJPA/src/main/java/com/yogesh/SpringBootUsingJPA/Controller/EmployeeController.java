package com.yogesh.SpringBootUsingJPA.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.SpringBootUsingJPA.DTO.EmployeeDTO;
import com.yogesh.SpringBootUsingJPA.DTO.ResponseErrorDTO;
import com.yogesh.SpringBootUsingJPA.DTO.ResponseWrapperDTO;
import com.yogesh.SpringBootUsingJPA.Model.Employee;
import com.yogesh.SpringBootUsingJPA.Repository.IEmployeeRepository;
import com.yogesh.SpringBootUsingJPA.Service.Impl.EmployeeServiceImpl;
import com.yogesh.SpringBootUsingJPA.Util.MethodsUtil;

@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeController {

	@Autowired
	IEmployeeRepository iEmployeeRepository;

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;

	@GetMapping(path = "/findAll")
	public ResponseEntity<?> findAll(HttpServletRequest httpServletRequest) {

		List<Employee> list = iEmployeeRepository.findAll();
		try {
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(list) ? null : list));
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ResponseErrorDTO("Exception in java",
					HttpServletResponse.SC_BAD_REQUEST,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/id/{id}")
	public ResponseEntity<?> findById(@PathVariable(value = "id") String id, HttpServletRequest httpServletRequest) {
		Optional<Employee> opEmpl = iEmployeeRepository.findById(id);

		try {
			if (opEmpl.equals(null)) {
				return new ResponseEntity<Object>(new ResponseErrorDTO("No Employee available",
						HttpServletResponse.SC_BAD_REQUEST,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of employee is present"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), opEmpl.get()));
		} catch (Exception e) {
			return new ResponseEntity<Object>(new ResponseErrorDTO("Exception in java",
					HttpServletResponse.SC_BAD_REQUEST,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/save")
	public ResponseEntity<?> save(@RequestBody EmployeeDTO employeeDTO, HttpServletRequest httpServletRequest) {
		try {
			if (MethodsUtil.isNullOrEmpty(employeeDTO.getUsername())) {
				return new ResponseEntity<Object>(
						new ResponseErrorDTO("Please Enter Username", HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest)),
						HttpStatus.BAD_REQUEST);
			}
			if (employeeDTO.getUsername().length() > 50) {
				return new ResponseEntity<Object>(
						new ResponseErrorDTO("Username must be less than 50 character",
								HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest)),
						HttpStatus.BAD_REQUEST);
			}
			Employee newEmployee = new Employee();
			newEmployee.setFistname(employeeDTO.getFistname());
			newEmployee.setLastname(employeeDTO.getLastname());
			newEmployee.setUsername(employeeDTO.getUsername());
			newEmployee.setPanid(employeeDTO.getPanid());
			newEmployee.setSalary(employeeDTO.getSalary());
			employeeServiceImpl.save(newEmployee);
			return ResponseEntity.ok(new ResponseWrapperDTO("Employee Saved", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), newEmployee));

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable(value = "id") String id,
			HttpServletRequest httpServletRequest) {
		try {
			if (MethodsUtil.isNullOrEmpty(employeeDTO.getUsername())) {
				return new ResponseEntity<Object>(
						new ResponseErrorDTO("Please Enter Username", HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest)),
						HttpStatus.BAD_REQUEST);
			}
			if (employeeDTO.getUsername().length() > 50) {
				return new ResponseEntity<Object>(
						new ResponseErrorDTO("Username must be less than 50 character",
								HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest)),
						HttpStatus.BAD_REQUEST);
			}

			Optional<Employee> opEmpl = iEmployeeRepository.findById(id);
			if (opEmpl.equals(null)) {
				return new ResponseEntity<Object>(new ResponseErrorDTO("No Employee available",
						HttpServletResponse.SC_BAD_REQUEST,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of employee is present"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Employee newEmployee = opEmpl.get();

			if (!employeeDTO.getUsername().equalsIgnoreCase(newEmployee.getUsername())) {
				Optional<Employee> employeeByUsername = iEmployeeRepository.findByUsername(employeeDTO.getUsername());
				return new ResponseEntity<Object>(
						new ResponseWrapperDTO("Username already Exists", HttpServletResponse.SC_BAD_REQUEST,
								MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), employeeByUsername),
						HttpStatus.BAD_REQUEST);
			}
			newEmployee.setFistname(employeeDTO.getFistname());
			newEmployee.setLastname(employeeDTO.getLastname());
			newEmployee.setUsername(employeeDTO.getUsername());
			newEmployee.setPanid(employeeDTO.getPanid());
			newEmployee.setSalary(employeeDTO.getSalary());
			newEmployee = employeeServiceImpl.saveAndFlush(newEmployee);
			System.out.println("newEmployee: " + newEmployee.getFistname());
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(newEmployee) ? new ArrayList<String>() : newEmployee));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") String id,
			HttpServletRequest httpServletRequest) {
		Optional<Employee> opEmpl = iEmployeeRepository.findById(id);
		try {
			if (opEmpl.equals(null)) {
				return new ResponseEntity<Object>(new ResponseErrorDTO("No Employee available for id: " + id,
						HttpServletResponse.SC_BAD_REQUEST,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of employee is present"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			employeeServiceImpl.delete(opEmpl.get());
			return ResponseEntity.ok(new ResponseWrapperDTO("Employee deleted", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), opEmpl));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/detail/{id}")
	public ResponseEntity<?> retriveEmployeeDetail(@PathVariable(value = "id") String id,
			HttpServletRequest httpServletRequest) {

		try {
			Optional<EmployeeDTO> opEmpl = iEmployeeRepository.retriveEmployeePanIdUsername(id);
			return ResponseEntity.ok(new ResponseWrapperDTO("Employee deleted", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(opEmpl) ? null : opEmpl));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * Paggination
	 */
	@GetMapping("/page/{pageno}/{perpage}")
	public ResponseEntity<?> findEmployeeByPage(@PathVariable(value = "pageno") String pageNo,
			@PathVariable(value = "perpage") String perPage, HttpServletRequest httpServletRequest) {
		if (MethodsUtil.isNullOrEmpty(pageNo)) {
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Page no is null " + pageNo, HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid page no"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (MethodsUtil.isNullOrEmpty(perPage)) {
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Per Page no is null " + perPage, HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid per page no"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Integer page_no = Integer.parseInt(pageNo);
		Integer per_page = Integer.parseInt(perPage);
		
		if(page_no < 1) {
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Page no is invalid " + page_no, HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid page no"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(per_page < 1) {
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("per Page employee is invalid " + per_page, HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid per page no"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		page_no = page_no - 1;

		Pageable pageable = PageRequest.of(page_no, per_page);
		
		Page<Employee> page = iEmployeeRepository.findAllPage(pageable);
		return ResponseEntity.ok(new ResponseWrapperDTO("Employee deleted", HttpServletResponse.SC_OK,
				MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
				MethodsUtil.isNullOrEmpty(page) ? null : page));
	}
}
