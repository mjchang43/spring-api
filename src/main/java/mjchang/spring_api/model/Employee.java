package mjchang.spring_api.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {

	@Id
	@Column(name = "id")
    private Integer id;
	
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "gender", nullable = false, length = 1)
    private String gender;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "age", nullable = false)
    private Integer age;
    
    @Column(name = "created_at", nullable = false)
    private Timestamp  created_at;
    
    @Column(name = "lastModify_at", nullable = false)
    private Timestamp lastModify_at;
    
    @ManyToOne
    @JoinColumn(name = "depId", nullable = false)
    private Department department;
    
    public Employee() {
    	
    }

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getLastModify_at() {
		return lastModify_at;
	}

	public void setLastModify_at(Timestamp lastModify_at) {
		this.lastModify_at = lastModify_at;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
    
}
