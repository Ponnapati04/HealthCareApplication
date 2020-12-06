package com.healthcare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.dao.DependentRepository;
import com.healthcare.model.Dependent;

@Service
public class DependentServiceImpl implements DependentService {

	@Autowired
	DependentRepository dependentRepository;

	@Override
	public Dependent addDependent(Dependent dependent) {
		return dependentRepository.save(dependent);
	}

	@Override
	public List<Dependent> getAllDependents(Long enrolleeId) {
		return dependentRepository.findByEnrolleeId(enrolleeId);
	}

	@Override
	public Dependent getDependent(Long id) {
		return dependentRepository.findById(id).orElse(null);
	}

	@Override
	public Dependent updateDependent(Dependent updatedDependent, Dependent dependent, Long id) {
		buildDependent(updatedDependent, dependent);
		updatedDependent.setId(id);
		return dependentRepository.save(updatedDependent);
	}

	@Override
	public void deleteDependent(Long id) {
		dependentRepository.deleteById(id);

	}

	private void buildDependent(Dependent updatedDependent, Dependent dependent) {

		if (dependent.getName() != null) {
			updatedDependent.setName(dependent.getName());
		}
		if (dependent.getBirthDate() != null) {
			updatedDependent.setBirthDate(dependent.getBirthDate());
		}

	}

}
