package com.healthcare.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.healthcare.contoller.EnrolleeController;
import com.healthcare.model.Enrollee;
import com.healthcare.service.EnrolleeService;
import com.healthcare.util.TestUtil;

@RunWith(SpringRunner.class)
@ContextConfiguration({ "classpath*:spring-test.xml" })
public class EnrolleeControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private EnrolleeService enrolleeService;

	private MockMvc mockMvc;

	private Enrollee enrollee1;

	private Enrollee enrollee2;

	private Enrollee enrollee3;

	@InjectMocks
	private EnrolleeController enrolleeController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(enrolleeController).build();
		enrollee1 = new Enrollee(1L, "enrollee1", true, new Date(), "7643278685");
		enrollee2 = new Enrollee(2L, "enrollee2", true, new Date(), "7643278687");
		enrollee3 = new Enrollee();
		enrollee3.setName("enrollee3");
		enrollee3.setActivationStatus(false);
	}

	@Test
	public void getAllEnrolleesTest() throws IOException, ParseException, Exception {

		List<Enrollee> enrollees = new ArrayList<Enrollee>();
		enrollees.add(enrollee1);
		enrollees.add(enrollee2);

		when(enrolleeService.getAllEnrollees()).thenReturn(enrollees);
		mockMvc.perform(get("/enrollees")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print());
		verify(enrolleeService, times(1)).getAllEnrollees();
		verifyNoMoreInteractions(enrolleeService);
	}

	@Test
	public void createEnrolleeFailTest() throws Exception {

		mockMvc.perform(post("/enrollees").contentType(APPLICATION_JSON_UTF8).content(TestUtil.ObjecttoJSON(enrollee3)))
				.andExpect(status().is4xxClientError()).andDo(print());
	}
	
	@Test
	public void createEnrolleeTest() throws Exception {

		when(enrolleeService.addEnrollee(Mockito.any(Enrollee.class))).thenReturn(enrollee1);
		mockMvc.perform(post("/enrollees").contentType(APPLICATION_JSON_UTF8).content(TestUtil.ObjecttoJSON(enrollee1)))
				.andExpect(status().is2xxSuccessful()).andDo(print());
		verify(enrolleeService, times(1)).addEnrollee(Mockito.any(Enrollee.class));
		verifyNoMoreInteractions(enrolleeService);
	}

	@Test
	public void getEnrolleeTest() throws Exception {

		when(enrolleeService.getEnrollee(1L)).thenReturn(enrollee1);

		mockMvc.perform(get("/enrollees/{id}", "1")).andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void getEnrolleeNotAvailableTest() throws Exception {
		when(enrolleeService.getEnrollee(1L)).thenReturn(null);

		mockMvc.perform(get("/enrollees/{id}", "1")).andExpect(status().is4xxClientError()).andDo(print());
	}

	@Test
	public void deleteEnrolleeTest() throws Exception {

		when(enrolleeService.getEnrollee(1L)).thenReturn(enrollee1);

		mockMvc.perform(delete("/enrollees/{id}", enrollee1.getId())).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void updateEnrolleeTest() throws Exception {
		when(enrolleeService.getEnrollee(enrollee2.getId())).thenReturn(enrollee1);
		when(enrolleeService.updateEnrollee(enrollee1, enrollee2, enrollee2.getId())).thenReturn(enrollee1);

		mockMvc.perform(put("/enrollees/{id}", enrollee2.getId()).contentType(APPLICATION_JSON_UTF8)
				.content(TestUtil.ObjecttoJSON(enrollee1))).andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void updateEnrolleeNotAvilableTest() throws Exception {

		when(enrolleeService.getEnrollee(enrollee1.getId())).thenReturn(null);

		mockMvc.perform(put("/enrollees/{id}", enrollee1.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.ObjecttoJSON(enrollee1))).andExpect(status().isNotFound()).andDo(print());

	}

	@Test
	public void deleteEnrolleeNotAvailableTest() throws Exception {
		when(enrolleeService.getEnrollee(1L)).thenReturn(null);

		mockMvc.perform(delete("/enrollees/{id}", "1")).andExpect(status().isNotFound()).andDo(print());

	}
	
}
