=> OneToMany

//Parent class
@Entity
@Table(name = VariableUtils.ParentsTableName)
public class Parents {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "parentsId")
	private String id;
	
	@OneToMany(mappedBy = "parents", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Childs> chiList;
}

@Entity
@Table(name = VariableUtils.ChildTableName)
public class Childs {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "childId")
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Parents parents;
	
	/*
	* Use @JsonIgnore to ignore recursion error
	*/
	@JsonIgnore
	public Parents getParents() {
		return parents;
	}
}