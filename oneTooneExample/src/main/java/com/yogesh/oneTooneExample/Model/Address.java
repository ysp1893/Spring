package com.yogesh.oneTooneExample.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yogesh.oneTooneExample.Util.VariableUtils;

@Entity
@Table(name = VariableUtils.AddressTableName)
public class Address {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "addressId")
	private String id;
	@Column(name = "street", columnDefinition = "varchar(50)")
	private String Street;
	@Column(name = "city", columnDefinition = "varchar(50)")
	private String city;
	@Column(name = "pincode", columnDefinition = "varchar(50)")
	private String pincode;

	/*
	* mappedBy: Used to tell db that this entity is mapped with "address" entity in mapping
	*/
	@OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Employee employee;

	public Address() {
	}

	public Address(String street, String city, String pincode) {
		super();
		Street = street;
		this.city = city;
		this.pincode = pincode;
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

	@JsonIgnore
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
