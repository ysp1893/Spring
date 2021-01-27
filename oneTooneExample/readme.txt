1. OneToOne Mapping

//parent object
@Entity
@Table(name = VariableUtils.EmployeeTableName)
public class Employee {
	/*
	* @Id: Represent Entity id
	* @GeneratedValue and GenericGenerator: Used for create system id
	*/

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "employeeId")
	private String id;

	//....more variables
	
	/*
	* @OneToOne: for one to one mapping
	* @JsonIgnoreProperties:  Annotation that can be used to either suppress serialization of properties (during serialization), 
	* or ignore processing of JSON properties read (during deserialization).
	*/
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	private Address address;

}

//Child object
@Entity
@Table(name = VariableUtils.AddressTableName)
public class Address {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "addressId")
	private String id;
	
	/*
	* mappedBy: Used to tell db that this entity is mapped with "address" entity in mapping
	*/
	@OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Employee employee;
}