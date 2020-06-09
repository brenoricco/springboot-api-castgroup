package com.breno.apicastgroup.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.breno.apicastgroup.dto.UserAuthenticateDTO;
import com.breno.apicastgroup.dto.CredentialsDTO;
import com.breno.apicastgroup.entities.User;
import com.breno.apicastgroup.repositories.UserRepository;
import com.breno.apicastgroup.security.JWTUtil;
import com.breno.apicastgroup.services.exception.InvalidAuthenticationException;
import com.breno.apicastgroup.services.exception.ObjectNotFoundException;

@Service
public class AuthenticationService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private JWTUtil jwt;

	public List<User> findAll() {
		return repo.findAll();
	}

	public UserAuthenticateDTO authenticate(CredentialsDTO obj) {
		if (obj == null)
			throw new ObjectNotFoundException("Objeto não encontrado");

		User user = repo.findByEmail(obj.getEmail());
		if (user == null)
			throw new ObjectNotFoundException("Usuário não encontrado");

		if (bcrypt.matches(obj.getPassword(), user.getPassword())) {
			String token = jwt.generateToken(user.getEmail());
			return new UserAuthenticateDTO(user.getEmail(), token, "Bearer ");
		} else {
			throw new InvalidAuthenticationException("Falha na autenticação usuário ou senha invalido");
		}
	}

}
