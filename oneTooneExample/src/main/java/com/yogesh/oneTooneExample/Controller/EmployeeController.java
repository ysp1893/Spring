package com.yogesh.oneTooneExample.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.yogesh.oneTooneExample.DTO.AddressDTO;
import com.yogesh.oneTooneExample.DTO.EmployeeDTO;
import com.yogesh.oneTooneExample.DTO.ResponseErrorDTO;
import com.yogesh.oneTooneExample.DTO.ResponseWrapperDTO;
import com.yogesh.oneTooneExample.Model.Address;
import com.yogesh.oneTooneExample.Model.Employee;
import com.yogesh.oneTooneExample.Repository.IAddressRepository;
import com.yogesh.oneTooneExample.Repository.IEmployeeRepository;
import com.yogesh.oneTooneExample.Service.Impl.AddressServiceImpl;
import com.yogesh.oneTooneExample.Service.Impl.EmployeeServiceImpl;
import com.yogesh.oneTooneExample.Util.MethodsUtil;

@RestController
@RequestMapping(path = "/api/employee")
public class EmployeeController {

	@Autowired
	IEmployeeRepository iEmployeeRepository;

	@Autowired
	EmployeeServiceImpl employeeServiceImpl;

	@Autowired
	IAddressRepository iAddressRepository;

	@Autowired
	AddressServiceImpl addressServiceImpl;

	@GetMapping(path = "/employee/findAll")
	public ResponseEntity<?> findAll(HttpServletRequest httpServletRequest) {

		try {
			List<Employee> elist = iEmployeeRepository.findAll();
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(elist) ? null : elist));
		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getCause().toString()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/address/findAll")
	public ResponseEntity<?> findAllAddress(HttpServletRequest httpServletRequest) {

		try {
			List<Address> alist = iAddressRepository.findAll();
			return ResponseEntity.ok(new ResponseWrapperDTO("Data fetched", HttpServletResponse.SC_OK,
					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
					MethodsUtil.isNullOrEmpty(alist) ? null : alist));
		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getCause().toString()),
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
	public ResponseEntity<?> save(@RequestBody(required = false) Optional<EmployeeDTO> emplDTO,
			HttpServletRequest httpServletRequest) {
		try {
			if (!emplDTO.isEmpty()) {
				EmployeeDTO employeeDTO = emplDTO.get();
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

				HashMap<String, Object> hashmap = new HashMap<String, Object>();
				Employee newEmployee = new Employee();
				newEmployee.setFistname(employeeDTO.getFistname());
				newEmployee.setLastname(employeeDTO.getLastname());
				newEmployee.setUsername(employeeDTO.getUsername());
				newEmployee.setPanid(employeeDTO.getPanid());
				newEmployee.setSalary(employeeDTO.getSalary());
				AddressDTO addressDTO = employeeDTO.getAddressDTO();
				Address address = new Address();
				newEmployee.setAddress(address);
				if (MethodsUtil.isNullOrEmpty(employeeDTO.getAddressDTO())) {
					System.out.println(".....Address value not available");
					hashmap.put("address", null);
				} else {
					System.out.println("AddressDTO: " + addressDTO.toString());
					address.setCity(addressDTO.getCity());
					address.setPincode(addressDTO.getPincode());
					address.setStreet(addressDTO.getStreet());
					System.out.println("add: "+address.getCity());
//					address.setEmployee(newEmployee);
					addressServiceImpl.save(address);
					System.out.println("Address: "+address.getId());
					hashmap.put("address", address);
				}
				employeeServiceImpl.save(newEmployee);
				hashmap.put("Employee", newEmployee);
				return ResponseEntity.ok(new ResponseWrapperDTO("Employee Detail Saved", HttpServletResponse.SC_OK,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), hashmap));
			} else {
				new ResponseErrorDTO("Null Value passed", HttpServletResponse.SC_BAD_REQUEST, null,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> update(@RequestBody Optional<EmployeeDTO> emplDTO, @PathVariable(value = "id") String id,
			HttpServletRequest httpServletRequest) {
		try {
			Optional<Employee> opEmpl = iEmployeeRepository.findById(id);
			if (opEmpl.isEmpty()) {
				return new ResponseEntity<Object>(new ResponseErrorDTO("No Employee available",
						HttpServletResponse.SC_BAD_REQUEST,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of employee is present"),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}

			if (!emplDTO.isEmpty()) {
				EmployeeDTO employeeDTO = emplDTO.get();
				System.out.println("employeeDTO: "+employeeDTO.getId());
				AddressDTO addressDTO = employeeDTO.getAddressDTO();
				System.out.println("AddressDTO Id: "+addressDTO.getId());
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

				HashMap<String, Object> hashmap = new HashMap<String, Object>();
				Employee newEmployee = opEmpl.get();
				Address newAddress = newEmployee.getAddress();
				System.out.println("");
				System.out.println("updated employee: "+newEmployee.getId());
				System.out.println("updated employee: "+newEmployee.getId());
				newEmployee.setFistname(employeeDTO.getFistname());
				newEmployee.setLastname(employeeDTO.getLastname());
				newEmployee.setUsername(employeeDTO.getUsername());
				newEmployee.setPanid(employeeDTO.getPanid());
				newEmployee.setSalary(employeeDTO.getSalary());				
				System.out.println();
				System.out.println("Address: "+newAddress.getCity()+" id: "+newAddress.getId());
				if (MethodsUtil.isNullOrEmpty(newAddress)) {
					System.out.println("Address value not available");
					hashmap.put("address", null);
				} else {
					newAddress.setCity(addressDTO.getCity());
					newAddress.setPincode(addressDTO.getPincode());
					newAddress.setStreet(addressDTO.getStreet());
//					newAddress.setEmployee(newEmployee);
					addressServiceImpl.save(newAddress);
					newAddress = addressServiceImpl.saveAndFlush(newAddress);
					hashmap.put("address", newAddress);
				}
				newEmployee = employeeServiceImpl.saveAndFlush(newEmployee);
				hashmap.put("Employee", newEmployee);
				return ResponseEntity.ok(new ResponseWrapperDTO("Employee Detail Updated", HttpServletResponse.SC_OK,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), hashmap));
			} else {
				new ResponseErrorDTO("Null Value passed", HttpServletResponse.SC_BAD_REQUEST, null,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(
					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(
				new ResponseErrorDTO("No Employee available", HttpServletResponse.SC_BAD_REQUEST,
						MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Non of employee is present"),
				HttpStatus.INTERNAL_SERVER_ERROR);

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

//	@GetMapping(path = "/detail/{id}")
//	public ResponseEntity<?> retriveEmployeeDetail(@PathVariable(value = "id") String id,
//			HttpServletRequest httpServletRequest) {
//
//		try {
//			Optional<EmployeeDTO> opEmpl = iEmployeeRepository.retriveEmployeePanIdUsername(id);
//			return ResponseEntity.ok(new ResponseWrapperDTO("Employee deleted", HttpServletResponse.SC_OK,
//					MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
//					MethodsUtil.isNullOrEmpty(opEmpl) ? null : opEmpl));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Object>(
//					new ResponseErrorDTO("Exception in java", HttpServletResponse.SC_BAD_REQUEST,
//							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	/*
	 * Paggination
	 */
//	@GetMapping("/page/{pageno}/{perpage}")
//	public ResponseEntity<?> findEmployeeByPage(@PathVariable(value = "pageno") String pageNo,
//			@PathVariable(value = "perpage") String perPage, HttpServletRequest httpServletRequest) {
//		if (MethodsUtil.isNullOrEmpty(pageNo)) {
//			return new ResponseEntity<Object>(
//					new ResponseErrorDTO("Page no is null " + pageNo, HttpServletResponse.SC_BAD_REQUEST,
//							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid page no"),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		if (MethodsUtil.isNullOrEmpty(perPage)) {
//			return new ResponseEntity<Object>(
//					new ResponseErrorDTO("Per Page no is null " + perPage, HttpServletResponse.SC_BAD_REQUEST,
//							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid per page no"),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		Integer page_no = Integer.parseInt(pageNo);
//		Integer per_page = Integer.parseInt(perPage);
//		
//		if(page_no < 1) {
//			return new ResponseEntity<Object>(
//					new ResponseErrorDTO("Page no is invalid " + page_no, HttpServletResponse.SC_BAD_REQUEST,
//							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid page no"),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		if(per_page < 1) {
//			return new ResponseEntity<Object>(
//					new ResponseErrorDTO("per Page employee is invalid " + per_page, HttpServletResponse.SC_BAD_REQUEST,
//							MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest), "Invalid per page no"),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		page_no = page_no - 1;
//
//		Pageable pageable = PageRequest.of(page_no, per_page);
//		
//		Page<Employee> page = iEmployeeRepository.findAllPage(pageable);
//		return ResponseEntity.ok(new ResponseWrapperDTO("Employee deleted", HttpServletResponse.SC_OK,
//				MethodsUtil.getApiPathFromHttpServletRequest(httpServletRequest),
//				MethodsUtil.isNullOrEmpty(page) ? null : page));
//	}
}
