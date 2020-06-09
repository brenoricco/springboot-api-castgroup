package com.breno.apicastgroup.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.breno.apicastgroup.entities.Employee;

public class EmployeeDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	private String name;
	private LocalDate birthDate;
	private LocalDate hiringDate;
	private String img;
	private String registration;
	
	public EmployeeDTO() {	
	}

	public EmployeeDTO(Employee obj) {
		super();
		id = obj.getId();
		name = obj.getName();
		email = obj.getEmail();
		birthDate = obj.getBirthDate();
		hiringDate = obj.getHiringDate();
		img = obj.getImg();
		registration = obj.getRegistration();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(LocalDate hiringDate) {
		this.hiringDate = hiringDate;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}	
}
