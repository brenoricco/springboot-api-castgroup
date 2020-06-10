package com.breno.apicastgroup.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EMPLOYEE")
public class Employee implements Serializable {

	private static final long serialVersionUID = 7887747266281678382L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private LocalDate birthDate;
	private LocalDate hiringDate;
	private String img;
	private String registration;

	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
	private Address address;

	@JsonIgnore
	@ManyToMany(mappedBy = "employees", cascade = CascadeType.REMOVE)
	private Set<Squad> squads = new HashSet<>();
	
	@JsonIgnore
	@OneToOne(mappedBy = "employee", cascade = CascadeType.REMOVE)
	private Vacation vacation;

	public Employee() {
	}

	public Employee(String name, String email, LocalDate birthDate, LocalDate hiringDate) {
		super();
		this.name = name;
		this.email = email;
		this.birthDate = birthDate;
		this.hiringDate = hiringDate;
		generator();
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
	
	public void generator() {
		Random random = new Random();
		registration = Integer.toString(random.hashCode());
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Squad> getSquads() {
		return squads;
	}

	public Vacation getVacation() {
		return vacation;
	}

	public void setVacation(Vacation vacation) {
		this.vacation = vacation;
	}
	
	@Override
	public String toString() {
		
		return 	"\nNome: " + name +
				"\nData de Nascimento: " + birthDate +
				"\nData de Contrato: " + hiringDate +
				"\nMatricula : " + registration;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
