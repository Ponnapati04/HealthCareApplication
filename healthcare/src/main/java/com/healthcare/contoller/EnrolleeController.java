package com.healthcare.contoller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.healthcare.exception.ResourceNotFoundException;
import com.healthcare.model.Enrollee;
import com.healthcare.service.EnrolleeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/enrollees")
public class EnrolleeController {

	@Autowired
	private EnrolleeService enrolleeService;

	@GetMapping
	public List<Enrollee> getAllEnrollees() {
		return enrolleeService.getAllEnrollees();
	}

	@ApiOperation(value = "get enrolee by id" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "Enrolle Found"),
			@ApiResponse(code = 404 , message = "Enrolle Not Found"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
	@GetMapping("{id}")
	public ResponseEntity<Object> getEnrollee(@PathVariable Long id) {
		Enrollee existingEnrollee = enrolleeService.getEnrollee(id);

		if (existingEnrollee != null) {
			return ResponseEntity.ok(existingEnrollee);
		} else {
			throw new ResourceNotFoundException("Enrollee not found with ID " + id);
		}
	}

	@ApiOperation(value = "crate enrolee" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 201 , message = "Enrolle Created"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
	@PostMapping
	public ResponseEntity<Object> createEnrollee(@Valid @RequestBody Enrollee enrollee) {
		Enrollee createdEnrollee = enrolleeService.addEnrollee(enrollee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdEnrollee.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@ApiOperation(value = "update enrolee" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "Enrolle Updated"),
			@ApiResponse(code = 404 , message = "Enrolle Not Found"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
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

	@ApiOperation(value = "delete enrolee by id" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "Enrolle Deleted"),
			@ApiResponse(code = 404 , message = "Enrolle Not Found"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
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
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
