package com.zensar.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zensar.helper.ExcelCreater;
import com.zensar.model.Student;
import com.zensar.response.ResponseMessage;
import com.zensar.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService service;

	@PostMapping("/save")
	public ResponseEntity<String> saveStudent(@RequestBody @Valid Student s) {
		ResponseEntity<String> resp = null;
		try {
			long id = service.saveStudent(s);
			resp = new ResponseEntity<String>(id + "saved", HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to save", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	@PostMapping("/saveAll")
	public ResponseEntity<?> saveAllStudents(@RequestBody List<Student> studentList) {

		ResponseEntity<?> resp = null;
		try {
			List<Student> studentresp = service.saveAllStudent(studentList);
			resp = new ResponseEntity<String>(studentresp + "Save All", HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to save", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return resp;
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllStudents() {
		ResponseEntity<?> resp = null;
		try {
			List<Student> list = service.getAllStudents();
			if (list != null && !list.isEmpty())
				resp = new ResponseEntity<List<Student>>(list, HttpStatus.OK);
			else
				resp = new ResponseEntity<String>("No Data Found", HttpStatus.OK);
		} catch (Exception e) {

			resp = new ResponseEntity<String>("Unable to fetch Data", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	@GetMapping("/one/{stdId}")
	public ResponseEntity<?> getOneStudent(@PathVariable Integer stdId) {
		ResponseEntity<?> resp = null;

		try {
			Optional<Student> opt = service.getOneStudent(stdId);
			if (opt.isPresent())
				resp = new ResponseEntity<Student>(opt.get(), HttpStatus.OK);
			else
				resp = new ResponseEntity<String>("No Data Found", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to fetch the Data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;

	}

	@DeleteMapping("/delete/{stdId}")
	public ResponseEntity<String> deleteStudent(@RequestBody long studentId) {

		ResponseEntity<String> resp = null;
		try {

			boolean exist = service.isExist(studentId);
			if (exist) {
				service.delete(studentId);
				resp = new ResponseEntity<String>(studentId + "removed", HttpStatus.OK);
			} else {
				resp = new ResponseEntity<String>(studentId + "Not Exist", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to delete", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	@PutMapping("/update/{stdId}")
	public ResponseEntity<String> updateStudent(@RequestBody Student s) {
		ResponseEntity<String> resp = null;
		try {
			boolean exist = service.isExist(s.getStdId());
			if (exist) {
				service.saveStudent(s);
				resp = new ResponseEntity<String>(s.getStdId() + "-Updated", HttpStatus.OK);
			} else {
				resp = new ResponseEntity<String>(s.getStdId() + "Not Exist", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to update", HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	@GetMapping("/exportExcel")
	public ResponseEntity<Resource> getFile() {
		String filename = "Student.xlsx";
		InputStreamResource file = new InputStreamResource(service.load());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestPart("file") MultipartFile file) {
		System.out.println("hello");
		String message = "";

		if (ExcelCreater.hasExcelFormat(file)) {
			System.out.println("Ramu");
			try {
				System.out.println("Abhi");
				service.save(file);
				System.out.println("Venkey");
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

}
