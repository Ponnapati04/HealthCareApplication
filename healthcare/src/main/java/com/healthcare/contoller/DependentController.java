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
import com.healthcare.model.Dependent;
import com.healthcare.model.Enrollee;
import com.healthcare.service.DependentService;
import com.healthcare.service.EnrolleeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/enrollees/{enrolleeId}/dependents")
public class DependentController {

	@Autowired
	private EnrolleeService enrolleeService;

	@Autowired
	private DependentService dependentService;

	@GetMapping
	public List<Dependent> getAllDependents(@PathVariable Long enrolleeId) {
		//validateEnrollee(enrolleeId);
		return dependentService.getAllDependents(enrolleeId);
	}

	@ApiOperation(value = "get dependents by  id" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "dependent found"),
			@ApiResponse(code = 404 , message = "dependent Not Found"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
	@GetMapping("{dependentId}")
	public ResponseEntity<Object> getDependents(@PathVariable Long enrolleeId, @PathVariable Long dependentId) {
		getEnrollee(enrolleeId);
		Dependent existingDependent = dependentService.getDependent(dependentId);

		if (existingDependent != null) {
			return ResponseEntity.ok(existingDependent);
		} else {
			throw new ResourceNotFoundException("Dependent not found with ID " + dependentId);
		}
	}

	@ApiOperation(value = "create dependents" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 201 , message = "dependent created"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
	@PostMapping
	public ResponseEntity<Object> createdDependent(@Valid @RequestBody Dependent dependent,
			@PathVariable Long enrolleeId) {
		Enrollee enrollee = getEnrollee(enrolleeId);
		dependent.setEnrollee(enrollee);
		Dependent createdDependent = dependentService.addDependent(dependent);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createdDependent.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@ApiOperation(value = "update dependents" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "dependent updated"),
			@ApiResponse(code = 404 , message = "dependent Not Found"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
	@PutMapping("{dependentId}")
	public ResponseEntity<Object> updateDependent(@PathVariable Long enrolleeId,@Valid @RequestBody Dependent dependent,
			@PathVariable Long dependentId) {
		Enrollee enrollee = getEnrollee(enrolleeId);
		Dependent existingDependent = dependentService.getDependent(dependentId);
		dependent.setEnrollee(enrollee);

		if (existingDependent != null) {
			dependentService.updateDependent(existingDependent, dependent, dependentId);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} else {
			throw new ResourceNotFoundException("Dependent not found with ID " + dependentId);
		}
	}

	
	@ApiOperation(value = "delete dependents" , response = ResponseEntity.class )
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "dependent deleted"),
			@ApiResponse(code = 404 , message = "dependent Not Found"),
			@ApiResponse(code = 400 , message = "Bad Request")
	})
	@DeleteMapping("{dependentId}")
	public ResponseEntity<Object> deleteDependent(@PathVariable Long enrolleeId, @PathVariable Long dependentId) {
		getEnrollee(enrolleeId);
		Dependent existingDependent = dependentService.getDependent(dependentId);

		if (existingDependent != null) {
			dependentService.deleteDependent(dependentId);
			return ResponseEntity.status(HttpStatus.OK).body("");
		} else {
			throw new ResourceNotFoundException("Dependent not found with ID " + dependentId);
		}

	}

	private Enrollee getEnrollee(Long enrolleeId) {
		Enrollee existingEnrollee = enrolleeService.getEnrollee(enrolleeId);

		if (existingEnrollee == null) {
			throw new ResourceNotFoundException("Enrollee not found with ID " + enrolleeId);
		}
		return existingEnrollee;
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
