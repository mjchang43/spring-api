package mjchang.spring_api.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import mjchang.spring_api.Application;
import mjchang.spring_api.configure.CustomConfigure;
import mjchang.spring_api.model.Department;
import mjchang.spring_api.model.Employee;
import mjchang.spring_api.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, CustomConfigure.class })
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
    @Autowired
    private EmployeeService service;
    
    @Test
    public void addEmployeeTest() throws Exception {
    	
    	Employee bob = new Employee();
    	bob.setId(13);
    	bob.setName("Bob");
    	bob.setGender("M");
    	bob.setAge(33);
    	bob.setPhone("0923456789");
    	bob.setAddress("Hsinchu");
    	bob.setDepartment(new Department(1,"design"));
    	bob.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
    	bob.setLastModify_at(Timestamp.valueOf(LocalDateTime.now()));
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bob);
        
        mockMvc.perform(post("/api/employee/add")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$.id", equalTo(13)))
        		.andExpect(jsonPath("$.department.id", equalTo(1)));
    }
    
    @Test
    public void employeeUpdateTest() throws Exception {
    	
    	Employee emp = service.findById(13);
    	emp.setName("David");
    	emp.setLastModify_at(Timestamp.valueOf(LocalDateTime.now()));
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(emp);
        
        mockMvc.perform(put("/api/employee/update/13")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$.name", equalTo("David")));
    }
    
    @Test
    public void findByIdTest() throws Exception {
    	
    	mockMvc.perform(get("/api/employee/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/employee/list")
        		.param("page", "1")
        		.param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$.content[0].id", equalTo(11)))
                .andExpect(jsonPath("$.content[1].id", equalTo(12)));
    }
    
    @Test
    public void findCustomTest() throws Exception {

    	Employee emp = new Employee();
    	emp.setPhone("092");
    	emp.setDepartment(new Department("maintain"));
    	
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(emp);
    	
    	mockMvc.perform(post("/api/employee/list")
        		.param("page", "0")
        		.param("size", "10")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", equalTo(2)));
    }
    
    @Test
    public void testDeleteById() throws Exception {
    	
        mockMvc.perform(delete("/api/employee/delete/13"))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", equalTo(true)));
    }
}
