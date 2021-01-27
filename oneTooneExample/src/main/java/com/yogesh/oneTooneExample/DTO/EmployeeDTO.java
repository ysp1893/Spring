package com.yogesh.oneTooneExample.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

	private String id;
	private String username;
	private String fistname;
	private String lastname;
	private String panid;
	private String salary;
	private AddressDTO addressDTO;

	public EmployeeDTO() {
	}

	public EmployeeDTO(String id, String username) {
		this.id = id;
		this.username = username;
	}

	public EmployeeDTO(String id, String username, String fistname, String lastname, String panid, String salary,
			AddressDTO addressDTO) {
		super();
		this.id = id;
		this.username = username;
		this.fistname = fistname;
		this.lastname = lastname;
		this.panid = panid;
		this.salary = salary;
		this.addressDTO = addressDTO;
	}

	public AddressDTO getAddressDTO() {
		return addressDTO;
	}

	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFistname() {
		return fistname;
	}

	public void setFistname(String fistname) {
		this.fistname = fistname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPanid() {
		return panid;
	}

	public void setPanid(String panid) {
		this.panid = panid;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", username=" + username + ", fistname=" + fistname + ", lastname=" + lastname
				+ ", panid=" + panid + ", salary=" + salary + ", addressDTO=" + addressDTO.toString() + "]";
	}
	
	

}
