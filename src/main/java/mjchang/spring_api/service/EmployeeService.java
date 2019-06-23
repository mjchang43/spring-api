package mjchang.spring_api.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mjchang.spring_api.Specification.EmployeeSpecification;
import mjchang.spring_api.model.Employee;
import mjchang.spring_api.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	public Iterable<Employee> findAll(PageRequest page) {
		
        try {
        	
        	return employeeRepository.findAll(page);
        }catch(Exception e) {
        	
        	e.printStackTrace();
        }
		return null;
    }
	
	public Iterable<Employee> findCustom(String req, PageRequest page) {
		
        try {
        	
        	ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> conditions = objectMapper.readValue(req, new TypeReference<Map<String, Object>>(){});

        	return employeeRepository.findAll(new EmployeeSpecification(conditions), page);
        }catch(Exception e) {
        	
        	e.printStackTrace();
        }
		return null;
    }

    public Employee findById(int id) {
    	
        try {
        	
        	return employeeRepository.getOne(id);
        }catch(Exception e) {
        	
        	e.printStackTrace();
        }
    	return null;
    }

    public Employee save(Employee employee) {
    	
    	try {
        	
        	return employeeRepository.save(employee);
    	}catch(Exception e) {
    		
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public void delete(int id) {
    	
    	employeeRepository.deleteById(id);
	}
}
