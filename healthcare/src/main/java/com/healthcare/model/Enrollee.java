package com.healthcare.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Enrollee implements Serializable {

	private static final long serialVersionUID = 9014957738246164241L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotNull(message = "Activation Status is mandatory")
	private Boolean activationStatus;

	@JsonFormat(pattern="yyyy-MM-dd")
	@NotNull(message = "Birth date is mandatory")
	private Date birthDate;

	@Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "Only numbers are accepted")
	private String phoneNumber;

	public Enrollee() {
	}

	public Enrollee(long id, String name, Boolean activationStatus, Date birthDate, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.activationStatus = activationStatus;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
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

	public Boolean getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(Boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Enrollee [id=" + id + ", name=" + name + ", activationStatus=" + activationStatus + ", birthDate="
				+ birthDate + ", phoneNumber=" + phoneNumber + "]";
	}

}
