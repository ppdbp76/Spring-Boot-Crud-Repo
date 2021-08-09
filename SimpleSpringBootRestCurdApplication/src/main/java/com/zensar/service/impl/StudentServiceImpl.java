package com.zensar.service.impl;

import java.io.ByteArrayInputStream;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.zensar.helper.ExcelCreater;
import com.zensar.model.Student;
import com.zensar.repo.StudentRepo;
import com.zensar.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo repo;

	@Override
	@Transactional
	public List<Student> saveAllStudent(List<Student> student) {
		List<Student> studentList = repo.saveAll(student);
		return studentList;
	}

	@Override
	public long saveStudent(Student s) {

		return repo.save(s).getStdId();
	}

	@Override
	public List<Student> getAllStudents() {

		return repo.findAll();
	}

	@Override
	public void delete(long id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Student> getOneStudent(long id) {

		return repo.findById(id);
	}

	@Override
	public boolean isExist(long id) {
		return repo.existsById(id);
	}
	public ByteArrayInputStream load() {
		List<Student> student = repo.findAll();
		ByteArrayInputStream in = ExcelCreater.tutorialsToExcel(student);
		return in;
	}

	@Override
	public void save(MultipartFile file) {

		try {
			System.out.println("ramaram");
			List<Student> student = ExcelCreater.excelToStudents(file.getInputStream());
			System.out.println("Ramcharan");
			repo.saveAll(student);
			System.out.println("SaveAll");
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}


	

}
