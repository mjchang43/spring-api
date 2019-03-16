package mjchang.spring_api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mjchang.spring_api.Specification.EmployeeSpecification;
import mjchang.spring_api.model.Department;
import mjchang.spring_api.model.Employee;
import mjchang.spring_api.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

	@TestConfiguration
    static class EmployeeServiceTestContextConfiguration {
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeService();
        }
    }
	
	@Autowired
    private EmployeeService service;
	
	@MockBean
    private EmployeeRepository employeeRepository;
	
	@Before
    public void setUp() {
        
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			Department dep1 = new Department(1, "design");
			Department dep2 = new Department(2, "maintain");
			Employee query = new Employee();
			query.setDepartment(dep1);
			String req = objectMapper.writeValueAsString(query);
			Map<String, Object> depConditions = objectMapper.readValue(req, new TypeReference<Map<String, Object>>(){});
			
			Employee david = new Employee();
			david.setId(13);
			david.setName("David");
			david.setPhone("0912345678");
			david.setDepartment(dep1);
	
	        Employee bob = new Employee();
	        bob.setId(14);
	        bob.setName("Bob");
	        bob.setPhone("0912345678");
	        bob.setDepartment(dep2);
	        
	        Employee emma = new Employee();
	        emma.setId(18);
	        emma.setName("Emma");
	        emma.setPhone("0912345678");
	        emma.setDepartment(dep1);
	        
	        PageRequest page = new PageRequest(0, 2, new Sort(Sort.Direction.ASC, "id"));
	        PageRequest page1 = new PageRequest(1, 2, new Sort(Sort.Direction.ASC, "id"));
	        Page<Employee> dep1Employees = new PageImpl<Employee>(Arrays.asList(david, emma), page, 2L);
	        Page<Employee> page1Employees = new PageImpl<Employee>(Arrays.asList(emma), page1, 3L);
	        
	        Mockito.when(employeeRepository.findAll(new EmployeeSpecification(depConditions), page)).thenReturn(dep1Employees);
	        Mockito.when(employeeRepository.findOne(david.getId())).thenReturn(david);
	        Mockito.when(employeeRepository.findOne(-99)).thenReturn(null);
	        Mockito.when(employeeRepository.findAll(page1)).thenReturn(page1Employees);
	        Mockito.when(employeeRepository.save(david)).thenReturn(david);
	        
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
	
	@Test
    public void whenValidDepartment_thenEmployeeShouldBeFound() throws Exception {
        
		ObjectMapper objectMapper = new ObjectMapper();
		
		Department dep1 = new Department(1, "design");
		Employee query = new Employee();
		query.setDepartment(dep1);
		
		PageRequest page = new PageRequest(0, 2, new Sort(Sort.Direction.ASC, "id"));
		String req = objectMapper.writeValueAsString(query);
		Iterable<Employee> found = service.findCustom(req, page);
		
        assertThat(found).hasSize(2).extracting(Employee::getName).contains("David", "Emma");
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll(Mockito.any(EmployeeSpecification.class), Mockito.any(PageRequest.class));
		Mockito.reset(employeeRepository);
    }

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() throws Exception {
        Employee fromDb = service.findById(13);
        assertThat(fromDb.getName()).isEqualTo("David");

        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findOne(Mockito.anyInt());
        Mockito.reset(employeeRepository);
    }

    @Test
    public void whenInValidId_thenEmployeeShouldNotBeFound() throws Exception {
        Employee fromDb = service.findById(-99);
        assertThat(fromDb).isNull();
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findOne(Mockito.anyInt());
        Mockito.reset(employeeRepository);
    }

    @Test
    public void givenEmployees_whengetAll_thenReturnRecords() throws Exception {
    	        
        PageRequest page = new PageRequest(1, 2, new Sort(Sort.Direction.ASC, "id"));
        Iterable<Employee> allEmployees = service.findAll(page);
        assertThat(allEmployees).hasSize(1).extracting(Employee::getName).contains("Emma");

        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll(Mockito.any(PageRequest.class));
        Mockito.reset(employeeRepository);
    }
    
    @Test
    public final void whenEntityIsCreated_thenReturnRecord() throws Exception {
    	Department dep1 = new Department(1, "design");
    	Employee david = new Employee();
		david.setId(13);
		david.setName("David");
		david.setPhone("0912345678");
		david.setDepartment(dep1);
		
    	Employee result = service.save(david);
    	assertThat(result.getName()).isEqualTo("David");
    	
    	Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).save(Mockito.any(Employee.class));
        Mockito.reset(employeeRepository);
    }
    
    @Test
    public final void whenEntityIsDeleted_thenCheckException() throws Exception {
    	
    	service.delete(13);
    }

}
