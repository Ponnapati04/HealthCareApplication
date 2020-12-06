package com.healthcare.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.healthcare.model.Dependent;

public interface DependentRepository extends CrudRepository<Dependent, Long> {

	List<Dependent> findByEnrolleeId(Long id);
}
 