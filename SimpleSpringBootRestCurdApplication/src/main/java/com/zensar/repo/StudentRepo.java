package com.zensar.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.zensar.model.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {

	boolean exists(Student studentId);

}
