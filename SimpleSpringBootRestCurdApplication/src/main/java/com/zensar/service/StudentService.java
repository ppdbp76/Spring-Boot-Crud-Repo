package com.zensar.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.zensar.model.Student;

public interface StudentService {
	
	public long saveStudent(Student s);
	public List<Student> saveAllStudent(List<Student> student);
	public List<Student> getAllStudents();
	public void delete(long id);
	public Optional<Student> getOneStudent(long id);
	public ByteArrayInputStream load();
	public void save(MultipartFile file);
	public boolean isExist(long id);
	


}
