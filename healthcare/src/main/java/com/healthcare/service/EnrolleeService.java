package com.healthcare.service;

import java.util.List;

import com.healthcare.model.Enrollee;

public interface EnrolleeService {

	Enrollee addEnrollee(Enrollee enrollee);

	List<Enrollee> getAllEnrollees();

	Enrollee getEnrollee(Long id);

	Enrollee updateEnrollee(Enrollee updatedEnrollee, Enrollee enrollee, Long id);

	void deleteEnrollee(Long id);
}
