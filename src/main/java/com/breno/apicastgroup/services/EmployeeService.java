package com.breno.apicastgroup.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.breno.apicastgroup.entities.Address;
import com.breno.apicastgroup.entities.Employee;
import com.breno.apicastgroup.entities.enums.State;
import com.breno.apicastgroup.repositories.EmployeeRepository;
import com.breno.apicastgroup.services.exception.InvalidInputDataException;
import com.breno.apicastgroup.services.exception.ObjectNotFoundException;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repo;

	public List<Employee> findAll() {
		return repo.findAll();
	}

	public Employee findById(Long id) {
		try {
			Optional<Employee> obj = repo.findById(id);
			if (obj.get() == null) {
				throw new ObjectNotFoundException("Funcionário não encontrado");
			}
			return obj.get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Funcionário não encontrado");
		}
	}

	public Employee insert(Employee obj) {
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
		if (obj.getRegistration() == null) {
			obj.generator();
		}
		obj.getAddress().setState(State.valueOf(obj.getAddress().getState().ordinal()));
		repo.save(obj);
		obj.setAddress(addressEmployeeInstantiate(obj));

		return repo.save(obj);
	}

	public void delete(Long id) {
		if (findById(id) != null) {
			repo.deleteById(id);
		}
	}

	public Employee update(Long id, Employee obj) {
		Employee entity = findById(id);
		if (obj == null) {
			throw new InvalidInputDataException("Dados não encontrados para a atualização do cadastro do funcionario.");
		}
		updateData(entity, obj);
		return repo.save(entity);
	}

	private void updateData(Employee entity, Employee obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setBirthDate(obj.getBirthDate());
		entity.setHiringDate(obj.getHiringDate());
		entity.setImg(obj.getImg());

		entity.getAddress().setStreet(obj.getAddress().getStreet());
		entity.getAddress().setNumber(obj.getAddress().getNumber());
		entity.getAddress().setComplement(obj.getAddress().getComplement());
		entity.getAddress().setNeighborhood(obj.getAddress().getNeighborhood());
		entity.getAddress().setCity(obj.getAddress().getCity());
		entity.getAddress().setState(State.valueOf(obj.getAddress().getState().ordinal()));
	}

	private Address addressEmployeeInstantiate(Employee obj) {
		Address address = new Address(obj.getAddress().getStreet(), obj.getAddress().getNumber(),
				obj.getAddress().getComplement(), obj.getAddress().getNeighborhood(), obj.getAddress().getCity(),
				obj.getAddress().getState(), obj);
		return address;
	}

	public Path saveImage(MultipartFile imageFile, Long id){
		Employee obj = findById(id);
		
		String folder = "./uploads/";
		String fileName = obj.getRegistration() + imageFile.getOriginalFilename();
		try {
			if(obj.getImg() != null) {
				File file = new File(folder + obj.getImg()); 
				file.delete();
			}
			byte[] bytes = imageFile.getBytes();
			Path path = Paths.get(folder + fileName);	
			Files.write(path, bytes);
			obj.setImg(fileName);
			repo.save(obj);
			
			return path;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
