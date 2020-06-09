package com.breno.apicastgroup.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breno.apicastgroup.dto.EmployeeDTO;
import com.breno.apicastgroup.dto.SquadDTO;
import com.breno.apicastgroup.entities.Squad;
import com.breno.apicastgroup.repositories.SquadRepository;
import com.breno.apicastgroup.services.exception.InvalidInputDataException;
import com.breno.apicastgroup.services.exception.ObjectNotFoundException;

@Service
public class SquadService {
	
	@Autowired
	SquadRepository repo;
	
	public List<Squad> findAll() {
		return repo.findAll();
	}
	
	public Squad findById(Long id) {				
		try {
			Optional<Squad> obj = repo.findById(id);
			if(obj.get() == null) {
				throw new ObjectNotFoundException("Equipe não encontrada");
			}
			
			return obj.get();
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("Equipe não encontrada");
		} 
	}
	
	public Squad insert(Squad obj) {
		if(obj == null) {
			throw new ObjectNotFoundException("Dados não encontrados para cadastro");
		}
		return repo.save(obj);
	}
	
	public void delete(Long id) {
		if(findById(id) != null) {
			repo.deleteById(id);
		}
	}
	
	public Squad update(Long id, Squad obj) {
			Squad entity = findById(id);
			if(obj == null) {
				throw new InvalidInputDataException("Dados não encontrados para a atualização do cadastro da equipe.");
			}
			updateData(entity, obj);
			return repo.save(entity);
	}
	
	public void updateData(Squad entity, Squad obj) {
		entity.setName(obj.getName());
	}
	
	public SquadDTO toDTO(Squad obj) {
		SquadDTO objDto = new SquadDTO(obj);
		obj.getEmployees().forEach(e -> objDto.getEmployees().add(new EmployeeDTO(e)));
		return objDto;
	}
	
	public List<SquadDTO> toListDTO(List<Squad> list) {
		List<SquadDTO> listDto = new ArrayList<>();
		list.forEach(e -> listDto.add(toDTO(e)));
		return listDto;
	}

}
