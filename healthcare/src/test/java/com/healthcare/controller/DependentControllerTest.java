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

import com.healthcare.contoller.DependentController;
import com.healthcare.model.Dependent;
import com.healthcare.model.Enrollee;
import com.healthcare.service.DependentService;
import com.healthcare.service.EnrolleeService;
import com.healthcare.util.TestUtil;

@RunWith(SpringRunner.class)
@ContextConfiguration({ "classpath*:spring-test.xml" })
public class DependentControllerTest {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Mock
	private EnrolleeService enrolleeService;

	@Mock
	private DependentService dependentService;

	private MockMvc mockMvc;

	private Enrollee enrollee1;

	private Enrollee enrollee2;

	private Enrollee enrollee3;

	private Dependent dependent1;

	private Dependent dependent2;

	private Dependent dependent3;

	@InjectMocks
	private DependentController dependentController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(dependentController).build();
		enrollee1 = new Enrollee(1L, "enrollee1", true, new Date(), "7643278685");
		enrollee2 = new Enrollee(2L, "enrollee2", true, new Date(), "7643278687");
		enrollee3 = new Enrollee();
		enrollee3.setName("enrollee3");
		enrollee3.setActivationStatus(false);
		dependent1 = new Dependent(1L, "dependent1", new Date(), enrollee1);
		dependent2 = new Dependent(2L, "dependent2", new Date(), enrollee2);
		when(enrolleeService.getEnrollee(enrollee1.getId())).thenReturn(enrollee1);
		dependent3 = new Dependent();
		dependent3.setName("dependent3");
	}

	@Test
	public void getAllDependentsTest() throws IOException, ParseException, Exception {

		List<Dependent> dependents = new ArrayList<Dependent>();
		dependents.add(dependent1);
		dependents.add(dependent2);

		when(dependentService.getAllDependents(enrollee1.getId())).thenReturn(dependents);
		mockMvc.perform(get("/enrollees/{enrolleeId}/dependents", enrollee1.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).andDo(print());
		verify(dependentService, times(1)).getAllDependents(1L);
		verifyNoMoreInteractions(dependentService);
	}

	@Test
	public void createDependentFailTest() throws Exception {

		mockMvc.perform(post("/enrollees/{enrolleeId}/dependents", enrollee1.getId()).contentType(APPLICATION_JSON_UTF8)
				.content(TestUtil.ObjecttoJSON(dependent3))).andExpect(status().is4xxClientError()).andDo(print());

	}
	
	@Test
	public void createDependentTest() throws Exception {

		when(dependentService.addDependent(Mockito.any(Dependent.class))).thenReturn(dependent1);
		mockMvc.perform(post("/enrollees/{enrolleeId}/dependents", enrollee1.getId()).contentType(APPLICATION_JSON_UTF8)
				.content(TestUtil.ObjecttoJSON(dependent1))).andExpect(status().is2xxSuccessful()).andDo(print());
		verify(dependentService, times(1)).addDependent(Mockito.any(Dependent.class));
		verifyNoMoreInteractions(dependentService);

	}

	@Test
	public void getDependentTest() throws Exception {

		when(dependentService.getDependent(dependent1.getId())).thenReturn(dependent1);

		mockMvc.perform(get("/enrollees/{enrolleeId}/dependents/{dependentId}", enrollee1.getId(), dependent1.getId()))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void getDependentNotAvailableTest() throws Exception {
		when(dependentService.getDependent(dependent1.getId())).thenReturn(null);

		mockMvc.perform(get("/enrollees/{enrolleeId}/dependents/{dependentId}", enrollee1.getId(), dependent1.getId()))
				.andExpect(status().is4xxClientError()).andDo(print());
	}

	@Test
	public void deleteDependentTest() throws Exception {

		when(dependentService.getDependent(dependent1.getId())).thenReturn(dependent1);

		mockMvc.perform(
				delete("/enrollees/{enrolleeId}/dependents/{dependentId}", enrollee1.getId(), dependent1.getId()))
				.andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void deleteDependentNotAvailableTest() throws Exception {

		when(dependentService.getDependent(dependent1.getId())).thenReturn(null);

		mockMvc.perform(
				delete("/enrollees/{enrolleeId}/dependents/{dependentId}", enrollee1.getId(), dependent1.getId()))
				.andExpect(status().is4xxClientError()).andDo(print());

	}

	@Test
	public void updateDependentTest() throws Exception {
		when(dependentService.getDependent(dependent2.getId())).thenReturn(dependent1);
		when(dependentService.updateDependent(dependent1, dependent2, dependent2.getId())).thenReturn(dependent1);
		mockMvc.perform(put("/enrollees/{enrolleeId}/dependents/{dependentId}", enrollee1.getId(), dependent2.getId())
				.contentType(APPLICATION_JSON_UTF8).content(TestUtil.ObjecttoJSON(dependent2)))
				.andExpect(status().isOk()).andDo(print());

	}

	@Test
	public void updateDependentNotAvailableTest() throws Exception {
		when(dependentService.getDependent(dependent2.getId())).thenReturn(null);
		mockMvc.perform(put("/enrollees/{enrolleeId}/dependents/{dependentId}", enrollee1.getId(), dependent2.getId())
				.contentType(APPLICATION_JSON_UTF8).content(TestUtil.ObjecttoJSON(dependent2)))
				.andExpect(status().is4xxClientError()).andDo(print());

	}

}
