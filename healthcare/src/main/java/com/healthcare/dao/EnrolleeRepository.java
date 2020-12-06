package com.healthcare.dao;

import org.springframework.data.repository.CrudRepository;

import com.healthcare.model.Enrollee;

public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {

}
