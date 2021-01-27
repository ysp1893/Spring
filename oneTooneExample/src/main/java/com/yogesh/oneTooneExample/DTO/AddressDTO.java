package com.yogesh.oneTooneExample.DTO;


public class AddressDTO {
	private String id;
	private String Street;
	private String city;
	private String pincode;
	private EmployeeDTO employeeDTO;
	
	
	
	public AddressDTO(String street, String city, String pincode, EmployeeDTO employeeDTO) {
		super();
		Street = street;
		this.city = city;
		this.pincode = pincode;
		this.employeeDTO = employeeDTO;
	}
	public AddressDTO() {}
	public EmployeeDTO getEmployeeDTO() {
		return employeeDTO;
	}
	public void setEmployeeDTO(EmployeeDTO employeeDTO) {
		this.employeeDTO = employeeDTO;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStreet() {
		return Street;
	}
	public void setStreet(String street) {
		Street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", Street=" + Street + ", city=" + city + ", pincode=" + pincode
				+ ", employeeDTO=" + employeeDTO + "]";
	}

	
	
}
