package com.healthcare.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dependent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@JsonFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "Birth date is mandatory")
	private Date birthDate;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnore
	private Enrollee enrollee;

	public Dependent() {

	}

	public Dependent(long id, String name, Date birthDate, Enrollee enrollee) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.enrollee = enrollee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Enrollee getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Enrollee enrollee) {
		this.enrollee = enrollee;
	}

	@Override
	public String toString() {
		return "Dependent [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", enrollee=" + enrollee + "]";
	}

}
