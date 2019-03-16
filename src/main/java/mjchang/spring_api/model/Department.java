package mjchang.spring_api.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="department")
public class Department {

	@Id
	@Column(name = "id")
    private Integer id;
	
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
    
    public Department() {
    	
    }
    
    public Department(int id, String name) {
    	
    	this.id = id;
    	this.name = name;
    }
    
    public Department(String name, Employee... employees) {
    	
        this.name = name;
        this.employees = Stream.of(employees).collect(Collectors.toList());
        this.employees.forEach(x -> x.setDepartment(this));
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
