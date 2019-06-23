package mjchang.spring_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mjchang.spring_api.model.Department;
import mjchang.spring_api.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
    private DepartmentService departmentService;
	
	@GetMapping("/{id}")
    public Department findById(@PathVariable("id") int id) {
		
        return departmentService.findById(id);
    }

    @GetMapping("/list")
    public Iterable<Department> findAll(@RequestParam(value = "page", defaultValue = "0") int page, 
    		@RequestParam(value = "size", defaultValue = "10") int size) {
    	
        page = page < 0 ? 0 : page;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return departmentService.findAll(PageRequest.of(page, size, sort));
    }

    @PostMapping("/add")
    public ResponseEntity<Department> addEmployee(@RequestBody Department dep) {

    	return ResponseEntity.ok().body(departmentService.save(dep));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody Department department) {

    	Department dep = departmentService.findById(id);
    	if(dep != null)
    		return ResponseEntity.ok().body(departmentService.save(department));
    	
    	return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") int id) {
    	
    	Department dep = departmentService.findById(id);
    	if(dep != null) {
    		
    		departmentService.delete(id);
    		return ResponseEntity.ok().body(true);
    	}
    	
        return ResponseEntity.notFound().build();
    }
}
