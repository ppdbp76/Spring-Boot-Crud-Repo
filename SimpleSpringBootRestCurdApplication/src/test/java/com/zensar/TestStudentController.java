package com.zensar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zensar.controller.StudentController;
import com.zensar.model.Student;
import com.zensar.service.StudentService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestStudentController {
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private StudentService service;
	
	@InjectMocks
	private StudentController controller;
	
	@Before
	public void  setUp() throws Exception{
		mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	@Disabled
	public void testSaveStudent() throws Exception {

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/student/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"stdId\":1,\"stdName\":\"Abhi\",\"stdCource\":\"Java\",\"stdFee\":10000.69}");
		MvcResult result = mockMvc.perform(request).andReturn();
		MockHttpServletResponse resp = result.getResponse();
		assertEquals(200, resp.getStatus());
		assertEquals("text/plain;charset=UTF-8", resp.getContentType());
		if (!resp.getContentAsString().contains("saved")) {
			fail("Data is not saved");
		}

	}
	/*
	 * public void testSave() throws Exception { Student s = new Student(3,
	 * "Venkey", "Archtech", 10000.30);
	 * Mockito.when(service.saveStudent(s)).thenReturn(s)
	 * mockMvc.perform(post("/student/save")).andExpect(status().isOk());
	 * 
	 * 
	 * }
	 */
	
	@Test
	void testStudentRemove() throws Exception {
	MockHttpServletRequestBuilder request=	MockMvcRequestBuilders
		.delete("/student/delete/1");
	MvcResult result= mockMvc.perform(request).andReturn();
		MockHttpServletResponse resp= result.getResponse();
		assertEquals(200,resp.getStatus());
		if(!resp.getContentAsString().contains("removed")) {
			fail("Not Removed");
		}
		
	}

}
