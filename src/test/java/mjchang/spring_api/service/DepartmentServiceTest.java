package mjchang.spring_api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

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

import mjchang.spring_api.model.Department;
import mjchang.spring_api.repository.DepartmentRepository;

@RunWith(SpringRunner.class)
public class DepartmentServiceTest {

	@TestConfiguration
    static class DepartmentServiceTestContextConfiguration {
        @Bean
        public DepartmentService departmentService() {
            return new DepartmentService();
        }
    }
	
	@Autowired
    private DepartmentService service;
	
	@MockBean
    private DepartmentRepository repository;
	
	@Before
    public void setUp() throws Exception {
        
		Department dep1 = new Department(1, "design");
		Department dep2 = new Department(2, "maintain");
		Department dep3 = new Department(3, "manager");
		
		PageRequest page = PageRequest.of(0, 2, new Sort(Sort.Direction.ASC, "id"));
		
		Page<Department> depList = new PageImpl<Department>(Arrays.asList(dep1, dep2), page, 2L);
		
        Mockito.when(repository.findAll(page)).thenReturn(depList);
        Mockito.when(repository.getOne(dep1.getId())).thenReturn(dep1);
        Mockito.when(repository.getOne(-99)).thenReturn(null);
        Mockito.when(repository.save(dep3)).thenReturn(dep3);
	       
    }
	
	@Test
    public void givenEmployees_whengetAll_thenReturnRecords() throws Exception {
        
		PageRequest page = PageRequest.of(0, 2, new Sort(Sort.Direction.ASC, "id"));
		Iterable<Department> found = service.findAll(page);
		
        assertThat(found).hasSize(2).extracting(Department::getName).contains("design", "maintain");
        Mockito.verify(repository, VerificationModeFactory.times(1)).findAll(Mockito.any(PageRequest.class));
		Mockito.reset(repository);
    }

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() throws Exception {
    	Department fromDb = service.findById(1);
        assertThat(fromDb.getName()).isEqualTo("design");

        Mockito.verify(repository, VerificationModeFactory.times(1)).getOne(Mockito.anyInt());
        Mockito.reset(repository);
    }

    @Test
    public void whenInValidId_thenEmployeeShouldNotBeFound() throws Exception {
    	Department fromDb = service.findById(-99);
        assertThat(fromDb).isNull();
        Mockito.verify(repository, VerificationModeFactory.times(1)).getOne(Mockito.anyInt());
        Mockito.reset(repository);
    }

    @Test
    public final void whenEntityIsCreated_thenReturnRecord() throws Exception {
    	
    	Department dep = new Department(3, "manager");
    	Department result = service.save(dep);
    	assertThat(result.getName()).isEqualTo("manager");
    	
    	Mockito.verify(repository, VerificationModeFactory.times(1)).save(Mockito.any(Department.class));
        Mockito.reset(repository);
    }
    
    @Test
    public final void whenEntityIsDeleted_thenCheckException() throws Exception {
    	
    	service.delete(3);
    }

}
