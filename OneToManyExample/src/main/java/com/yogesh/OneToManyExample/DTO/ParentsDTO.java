package com.yogesh.OneToManyExample.DTO;

import java.util.List;

public class ParentsDTO {
	
	private String id;
	private String fname;
	private String mname;
	private List<ChildsDTO> chiList;
	
	public ParentsDTO() {}

	public ParentsDTO(String fname, String mname) {
		super();
		this.fname = fname;
		this.mname = mname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public List<ChildsDTO> getChiList() {
		return chiList;
	}

	public void setChiList(List<ChildsDTO> chiList) {
		this.chiList = chiList;
	}

	
}
