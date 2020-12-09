package com.healthcare.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.healthcare.dao.DependentRepository;
import com.healthcare.model.Dependent;
import com.healthcare.model.Enrollee;

public class DependentServiceTest {

	@InjectMocks
	DependentServiceImpl dependentService;

	@Mock
	private DependentRepository dependentRepository;

	private Enrollee enrollee1;

	private Enrollee enrollee2;

	private Dependent dependent1;

	private Dependent dependent2;

	private Dependent dependent3;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		enrollee1 = new Enrollee(1L, "enrollee1", true, new Date(), "7643278685");
		enrollee2 = new Enrollee(2L, "enrollee2", true, new Date(), "7643278687");
//		enrollee3 = new Enrollee();
//		enrollee3.setName("enrollee3");
//		enrollee3.setActivationStatus(false);

		dependent1 = new Dependent(1L, "dependent1", new Date(), enrollee1);
		dependent2 = new Dependent(2L, "dependent2", new Date(), enrollee2);
		dependent3 = new Dependent();
		dependent3.setName("dependent3");
	}

	@Test
	public void getAllDependentsTest() throws ParseException {

		List<Dependent> dependents = new ArrayList<Dependent>();
		dependents.add(dependent1);
		dependents.add(dependent2);
		when(dependentRepository.findByEnrolleeId(enrollee1.getId())).thenReturn(dependents);
		List<Dependent> dependentsList = dependentService.getAllDependents(enrollee1.getId());
		assertEquals(2, dependentsList.size());
		verify(dependentRepository, times(1)).findByEnrolleeId(enrollee1.getId());

	}

	@Test
	public void createDependentTest() throws ParseException {
		when(dependentRepository.save(dependent1)).thenReturn(dependent1);
		dependentService.addDependent(dependent1);
		verify(dependentRepository, times(1)).save(dependent1);
	}

	@Test
	public void getDependentsTest() {
		when(dependentRepository.findById(dependent1.getId())).thenReturn(Optional.of(dependent1));
		Dependent dependent = dependentService.getDependent(dependent1.getId());
		assertEquals("dependent1", dependent.getName());
	}

	@Test
	public void deleteDependentTest() {
		dependentService.deleteDependent(dependent1.getId());
		verify(dependentRepository, times(1)).deleteById(dependent1.getId());
		verifyNoMoreInteractions(dependentRepository);
	}

	@Test
	public void updateDependentTest() {

		when(dependentRepository.save(dependent2)).thenReturn(dependent2);
		dependentService.updateDependent(dependent2, dependent1, dependent2.getId());
		verify(dependentRepository, times(1)).save(dependent2);

	}

}
