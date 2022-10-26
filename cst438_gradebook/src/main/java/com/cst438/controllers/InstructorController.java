package com.cst438.controllers;

import java.sql.Date;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.services.RegistrationService;

@RestController
public class InstructorController {
	
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	AssignmentGradeRepository assignmentGradeRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	RegistrationService registrationService;
	
	@GetMapping("/getAssignment")
	@Transactional
		public AssignmentListDTO.AssignmentDTO getExample(){
		Assignment a = assignmentRepository.findById(1).orElse(null);
		
		return new AssignmentListDTO.AssignmentDTO(a.getId(), a.getCourse().getCourse_id(), a.getName(), a.getDueDate().toString(),a.getCourse().getTitle());
	}
	
	@PostMapping("/assignment")
	public Integer AddAssignment(@RequestBody AssignmentListDTO.AssignmentDTO a) {
	
	System.out.println("" + a);
		
		int i = -1;
		
		Course assignmentCourse = courseRepository.findById(a.courseId).orElse(null);
			if(assignmentCourse == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "course not found");
			}
			else {
				Assignment newAssignment = new Assignment();
				newAssignment.setCourse(assignmentCourse);
				newAssignment.setName(a.assignmentName);
				newAssignment.setDueDate(java.sql.Date.valueOf(a.dueDate));
				newAssignment = assignmentRepository.save(newAssignment);
			
			
				for(Assignment iter : assignmentRepository.findAll()) {
					if (iter.getCourse().equals(newAssignment.getCourse()) && iter.getDueDate().equals(newAssignment.getDueDate()) && iter.getName().equals(newAssignment.getName())) {
						i = iter.getId();
					}
					
				}
				
		}
		return i;

		
		
		
	}

	@PatchMapping("/assignment/{id}/{name}")
	public void updateAssignment(@PathVariable("id") Integer id, @PathVariable("name") String newName) {
		Assignment temp = null;
		for(Assignment curAssign : assignmentRepository.findAll()) {
			if(curAssign.getId() == id) {
			temp = curAssign;
		}
	}
	if (temp == null) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id not found");
	}
	else {
		temp.setName(newName); //set the NewName
		assignmentRepository.save(temp);
	}
}
	
	@DeleteMapping("/assignment/{id}")
	public void updateAssignment(@PathVariable("id") Integer id) {
		Assignment temp = null;
		for(Assignment curAssign : assignmentRepository.findAll()) {
			if(curAssign.getId() == id) {
			temp = curAssign;
		}
	}
	if (temp == null) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id not found");
	}
	else {
		
		assignmentRepository.delete(temp);
	}
}
	

	
}
