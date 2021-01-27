package com.yogesh.OneToManyExample.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.yogesh.OneToManyExample.Util.VariableUtils;

@Entity
@Table(name = VariableUtils.ParentsTableName)
public class Parents {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "parentsId")
	private String id;
	
	@Column(name = "fname")
	private String fname;
	@Column(name = "mname")
	private String mname;
	
	@OneToMany(mappedBy = "parents", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Childs> chiList;
	
	public Parents() {}
	
	public Parents(String fname, String mname) {
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

	public List<Childs> getChiList() {
		return chiList;
	}

	public void setChiList(List<Childs> chiList) {
		this.chiList = chiList;
	}
	
	
	
}
