package com.breno.apicastgroup.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.breno.apicastgroup.entities.Squad;

public class SquadDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Set<EmployeeDTO> employees = new HashSet<>();
	
	public SquadDTO() {
	}
	
	public SquadDTO(Squad obj) {
		id = obj.getId();
		name = obj.getName();
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

	public Set<EmployeeDTO> getEmployees() {
		return employees;
	}
}
