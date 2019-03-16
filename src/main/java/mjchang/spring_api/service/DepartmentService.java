package mjchang.spring_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import mjchang.spring_api.model.Department;
import mjchang.spring_api.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
    private DepartmentRepository departmentRepository;
	
	public Iterable<Department> findAll(PageRequest page) {
		
        try {
        	
        	return departmentRepository.findAll(page);
        }catch(Exception e) {
        	
        	e.printStackTrace();
        }
		return null;
    }

    public Department findById(int id) {
    	
        try {
        	
        	return departmentRepository.findOne(id);
        }catch(Exception e) {
        	
        	e.printStackTrace();
        }
		return null;
    }

    public Department save(Department dep) {
    	
        try {
        	
        	return departmentRepository.save(dep);
        }catch(Exception e) {
        	
        	e.printStackTrace();
        }
		return null;
    }
    
    public void delete(int id) {
    	
    	try {
    		
    		departmentRepository.delete(id);
    	}catch(Exception e) {
        	
        	e.printStackTrace();
        }
	}
}
