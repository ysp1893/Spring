package com.yogesh.SpringBootUsingJPA.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.yogesh.SpringBootUsingJPA.Util.VariableUtils;

@Entity
@Table(name = VariableUtils.Employee_table_name, uniqueConstraints = {
		@UniqueConstraint(columnNames = { "username" }) })
public class Employee {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
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

	public Employee() {
	}

	public Employee(String id, String username, String fistname, String lastname, String panid, String salary) {
		super();
		this.id = id;
		this.username = username;
		this.fistname = fistname;
		this.lastname = lastname;
		this.panid = panid;
		this.salary = salary;
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
