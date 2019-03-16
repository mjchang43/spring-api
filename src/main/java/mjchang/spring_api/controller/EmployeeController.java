package mjchang.spring_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mjchang.spring_api.model.Employee;
import mjchang.spring_api.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;
	
	@GetMapping("/{id}")
    public Employee findById(@PathVariable("id") int id) {
		
        try {
        	return employeeService.findById(id);
        } catch (Exception e) {
        	
        }
		return null;
    }

    @GetMapping("/list")
    public Iterable<Employee> findAll(@RequestParam(value = "page", defaultValue = "0") int page, 
    		@RequestParam(value = "size", defaultValue = "10") int size) {
    	
        page = page < 0 ? 0 : page;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return employeeService.findAll(new PageRequest(page, size, sort));
    }
    
    @PostMapping("/list")
    public Iterable<Employee> findCustom(@RequestParam(value = "page", defaultValue = "0") int page, 
    		@RequestParam(value = "size", defaultValue = "10") int size, @RequestBody String req) {
    	
        page = page < 0 ? 0 : page;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return employeeService.findCustom(req, new PageRequest(page, size, sort));
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {

    	return ResponseEntity.ok().body(employeeService.save(employee));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) {

    	Employee emp = employeeService.findById(id);
    	if(emp != null)
    		return ResponseEntity.ok().body(employeeService.save(employee));
    	
    	return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") int id) {
    	
    	Employee emp = employeeService.findById(id);
    	if(emp != null) {
    		
    		employeeService.delete(id);
    		return ResponseEntity.ok().body(true);
    	}
    	
        return ResponseEntity.notFound().build();
    }

}
