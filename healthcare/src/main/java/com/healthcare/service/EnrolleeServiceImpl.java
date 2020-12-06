package com.healthcare.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.dao.EnrolleeRepository;
import com.healthcare.model.Enrollee;

@Service
public class EnrolleeServiceImpl implements EnrolleeService {

	@Autowired
	EnrolleeRepository enrolleeRepository;

	@Override
	public Enrollee addEnrollee(Enrollee enrollee) {
		return enrolleeRepository.save(enrollee);
	}

	@Override
	public List<Enrollee> getAllEnrollees() {
		return StreamSupport.stream(enrolleeRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Enrollee getEnrollee(Long id) {
		return enrolleeRepository.findById(id).orElse(null);
	}

	@Override
	public Enrollee updateEnrollee(Enrollee updatedEnrollee, Enrollee enrollee, Long id) {
		buildEnrollee(updatedEnrollee, enrollee);
		updatedEnrollee.setId(id);
		return enrolleeRepository.save(updatedEnrollee);

	}

	@Override
	public void deleteEnrollee(Long id) {
		enrolleeRepository.deleteById(id);

	}

	private void buildEnrollee(Enrollee updatedEnrollee, Enrollee enrollee) {
		if (enrollee.getActivationStatus() != null) {
			updatedEnrollee.setActivationStatus(enrollee.getActivationStatus());
		}
		if (enrollee.getName() != null) {
			updatedEnrollee.setName(enrollee.getName());
		}
		if (enrollee.getBirthDate() != null) {
			updatedEnrollee.setBirthDate(enrollee.getBirthDate());
		}
		if (enrollee.getPhoneNumber() != null) {
			updatedEnrollee.setPhoneNumber(enrollee.getPhoneNumber());
		}
	}

}
