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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yogesh.oneTooneExample.Util.VariableUtils;

/*
 * Parent object which store address child class
 */

@Entity
@Table(name = VariableUtils.EmployeeTableName)
public class Employee {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "employeeId")
	private String id;

	@Column(name = "username", columnDefinition = "varchar(50)")
	private String username;
	@Column(name = "firstname", columnDefinition = "varchar(25)")
	private String fistname;
	@Column(name = "lastname", columnDefinition = "varchar(25)")
	private String lastname;
	@Column(name = "panid", columnDefinition = "varchar(50)")
	private String panid;
	@Column(name = "salary", columnDefinition = "varchar(50)")
	private String salary;

	/*
	* @OneToOne: for one to one mapping
	* @JsonIgnoreProperties:  Annotation that can be used to either suppress serialization of properties (during serialization), 
	* or ignore processing of JSON properties read (during deserialization).
	*/
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Address address;

	public Employee() {}

	public Employee(String id, String username, String fistname, String lastname, String panid, String salary) {
		super();
		this.id = id;
		this.username = username;
		this.fistname = fistname;
		this.lastname = lastname;
		this.panid = panid;
		this.salary = salary;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
}
