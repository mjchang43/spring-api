package mjchang.spring_api.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { Application.class, CustomConfigure.class })
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
    public void findByIdTest() throws Exception {
    	
    	mockMvc.perform(get("/api/department/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void findAllTest() throws Exception {

        mockMvc.perform(get("/api/department/list")
        		.param("page", "0")
        		.param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$.content[0].id", equalTo(1)))
                .andExpect(jsonPath("$.content[1].id", equalTo(2)));
    }
    
    @Test
    public void findAllForPageTest() throws Exception {

        mockMvc.perform(get("/api/department/list")
        		.param("page", "0")
        		.param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$.content[0].id", equalTo(1)))
                .andExpect(jsonPath("$.content[1].id", equalTo(2)));
    }
    
    @Test
    public void addEmployeeTest() throws Exception {
    	
    	Department support = new Department(3, "support");
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(support);
        
        mockMvc.perform(post("/api/department/add")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$.id", equalTo(3)));
    }
    
    @Test
    public void employeeUpdateTest() throws Exception {
    	
    	Department manager = new Department(3, "manager");
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(manager);
        
        mockMvc.perform(put("/api/department/update/3")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(json))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$.name", equalTo("manager")));
    }
    
    @Test
    public void testDeleteById() throws Exception {
    	
        mockMvc.perform(delete("/api/department/delete/3"))
        		.andExpect(status().isOk())
        		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$", equalTo(true)));
    }
}
