package com.healthcare.service;

import java.util.List;

import com.healthcare.model.Dependent;

public interface DependentService {

	Dependent addDependent(Dependent dependent);

	List<Dependent> getAllDependents(Long enrolleeId);

	Dependent getDependent(Long id);

	Dependent updateDependent(Dependent updatedDepdent, Dependent dependent, Long id);

	void deleteDependent(Long id);
}
