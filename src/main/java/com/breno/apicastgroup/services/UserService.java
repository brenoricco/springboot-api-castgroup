package com.breno.apicastgroup.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.breno.apicastgroup.entities.User;
import com.breno.apicastgroup.repositories.UserRepository;
import com.breno.apicastgroup.services.exception.InvalidInputDataException;
import com.breno.apicastgroup.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(Long id) {				
		try {
			Optional<User> obj = repo.findById(id);
			if(obj.get() == null) {
				throw new ObjectNotFoundException("Usuário não encontrado");
			}
			return obj.get();
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("Usuário não encontrado");
		} 
	}
	
	public User insert(User obj) {
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
		obj.setPassword(bcrypt.encode(obj.getPassword()));
		repo.save(obj);
		return repo.save(obj);
	}
	
	public void delete(Long id) {
		if(findById(id) != null) {
			repo.deleteById(id);
		}
	}
	
	public User update(Long id, User obj) {
			User entity = findById(id);
			if(obj == null) {
				throw new InvalidInputDataException("Dados não encontrados para a atualização do cadastro de usuário.");
			}
			updateData(entity, obj);
			return repo.save(entity);
	}
	
	private void updateData(User entity, User obj) {
		entity.setEmail(obj.getEmail());
		entity.setPassword(obj.getPassword());
	}
}
