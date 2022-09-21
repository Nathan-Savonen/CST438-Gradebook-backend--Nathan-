package com.cst438.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;

@RestController
public class InstructorController {
	@PostMapping("/course")
	public void AddAssignment(@PathVariable int course_id) {
		
		String email = "dwisneski@csumb.edu";
		
		
	}

	public Assignment changeAssignmentName(int assignmentId) {
		
		Object assignmentRepository;
		
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
		
		if (assignment == null) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment not found. "+assignmentId );
		}
		
		return assignment;
	}
	@DeleteMapping("/course")
	public void deleteAssignment() {
		
	}
	
}
