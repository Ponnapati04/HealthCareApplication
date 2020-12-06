package com.healthcare.contoller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.model.Enrollee;
import com.healthcare.service.EnrolleeService;

@RestController
@RequestMapping("/enrollees")
public class EnrolleeController {

	@Autowired
	private EnrolleeService enrolleeService;

	@GetMapping
	public List<Enrollee> getAllEnrollees() {
		return enrolleeService.getAllEnrollees();
	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getEnrollee(@PathVariable Long id) {
		Enrollee existingEnrollee = enrolleeService.getEnrollee(id);

		if (existingEnrollee != null) {
			return ResponseEntity.ok(existingEnrollee);
		} else {
			throw new ResourceNotFoundException("Enrollee not found with ID " + id);
		}
	}

	@PostMapping
	public ResponseEntity<Object> createEnrollee(@Valid @RequestBody Enrollee enrollee) {
		Enrollee createdEnrollee = enrolleeService.addEnrollee(enrollee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdEnrollee.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("{id}")
	public ResponseEntity<Object> updateEnrollee(@Valid @RequestBody Enrollee enrollee, @PathVariable Long id) {
		Enrollee existingEnrollee = enrolleeService.getEnrollee(id);
		if (existingEnrollee != null) {
			enrolleeService.updateEnrollee(existingEnrollee, enrollee, id);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} else {
			throw new ResourceNotFoundException("Enrollee not found with ID " + id);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteEnrollee(@PathVariable Long id) {
		Enrollee enrollee = enrolleeService.getEnrollee(id);

		if (enrollee != null) {
			enrolleeService.deleteEnrollee(id);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} else {
			throw new ResourceNotFoundException("Enrollee not found with ID " + id);
		}

	}

}
