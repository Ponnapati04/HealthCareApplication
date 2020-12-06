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

import com.healthcare.dao.EnrolleeRepository;
import com.healthcare.model.Enrollee;

public class EnrolleeServiceTest {

	@InjectMocks
	EnrolleeServiceImpl enrolleeService;

	@Mock
	private EnrolleeRepository enrolleeRepository;

	private Enrollee enrollee1;

	private Enrollee enrollee2;

	private Enrollee enrollee3;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		enrollee1 = new Enrollee(1L, "enrollee1", true, new Date(), "7643278685");
		enrollee2 = new Enrollee(2L, "enrollee2", true, new Date(), "7643278687");
		enrollee3 = new Enrollee();
		enrollee3.setName("enrollee3");
		enrollee3.setActivationStatus(false);
	}

	@Test
	public void getAllEnrolleesTest() throws ParseException {

		List<Enrollee> enrollees = new ArrayList<Enrollee>();
		enrollees.add(enrollee1);
		enrollees.add(enrollee2);
		when(enrolleeRepository.findAll()).thenReturn(enrollees);
		List<Enrollee> enrolleesList = enrolleeService.getAllEnrollees();
		assertEquals(2, enrolleesList.size());
		verify(enrolleeRepository, times(1)).findAll();

	}

	@Test
	public void createEnrolleeTest() throws ParseException {

		when(enrolleeRepository.save(enrollee1)).thenReturn(enrollee1);
		enrolleeService.addEnrollee(enrollee1);
		verify(enrolleeRepository, times(1)).save(enrollee1);
	}

	@Test
	public void getEnrolleeTest() {
		when(enrolleeRepository.findById(enrollee1.getId())).thenReturn(Optional.of(enrollee1));
		Enrollee enrollee = enrolleeService.getEnrollee(enrollee1.getId());
		assertEquals("enrollee1", enrollee.getName());
	}

	@Test
	public void deleteEnrolleeTest() {
		enrolleeService.deleteEnrollee(enrollee1.getId());
		verify(enrolleeRepository, times(1)).deleteById(enrollee1.getId());
		verifyNoMoreInteractions(enrolleeRepository);
	}

	@Test
	public void updateEnrolleeTest() {

		when(enrolleeRepository.save(enrollee2)).thenReturn(enrollee2);
		enrolleeService.updateEnrollee(enrollee2, enrollee1, enrollee2.getId());
		verify(enrolleeRepository, times(1)).save(enrollee2);

	}

}
