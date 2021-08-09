package com.zensar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.zensar.model.Student;
import com.zensar.repo.StudentRepo;
import com.zensar.service.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class SimpleSpringBootRestCurdApplicationTests {
	@Autowired
	private StudentService service;
	@MockBean
	private StudentRepo repo;

	@Test()
	public void testSaveStudent() throws Exception {
		Student s = new Student(3, "Venkey", "Archtech", 10000.30);
		when(repo.save(s)).thenReturn(s);
		assertEquals(3, service.saveStudent(s));

	}

	@Test
	@Disabled
	public void testDeleteStudent() throws Exception {

		Student st = new Student();

		st.setStdId(3);
		st.setStdName("Venkey");
		st.setStdCource("Archtech");
		st.setStdFee(10000.30);

		service.delete(st.getStdId());
		verify(repo, times(1)).delete(st);
	}

}
