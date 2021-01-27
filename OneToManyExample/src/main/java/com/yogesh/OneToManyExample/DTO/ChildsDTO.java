package com.yogesh.OneToManyExample.DTO;

import com.yogesh.OneToManyExample.Model.Parents;

public class ChildsDTO {
	
	private String id;
	private String name;
	private String age;
	private String gender;
	private Parents parents;

	public ChildsDTO() {}

	public ChildsDTO(String name, String age, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Parents getParents() {
		return parents;
	}

	public void setParents(Parents parents) {
		this.parents = parents;
	}
}
