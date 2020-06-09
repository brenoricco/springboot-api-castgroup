package com.breno.apicastgroup.services;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.breno.apicastgroup.entities.Address;
import com.breno.apicastgroup.entities.Employee;
import com.breno.apicastgroup.entities.Squad;
import com.breno.apicastgroup.entities.User;
import com.breno.apicastgroup.entities.Vacation;
import com.breno.apicastgroup.entities.enums.State;
import com.breno.apicastgroup.repositories.EmployeeRepository;
import com.breno.apicastgroup.repositories.SquadRepository;
import com.breno.apicastgroup.repositories.UserRepository;
import com.breno.apicastgroup.repositories.VacationRepository;

@Service
public class DBService {
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	SquadRepository squadRepository;

	@Autowired
	VacationRepository vacationRepository;

	public void instantiateTestDatabase() throws ParseException {
		User user = new User("administrador.ferias@castgroup.com.br", bcrypt.encode("123a"));
		userRepository.save(user);

		Squad s1 = new Squad("Equipe A");
		Squad s2 = new Squad("Equipe B");
		Squad s3 = new Squad("Equipe C");

		squadRepository.saveAll(Arrays.asList(s1, s2, s3));

		Employee e1 = new Employee("Ronaldo Oliveira", "ronaldo@gmail.com", LocalDate.parse("1970-01-20"),
				LocalDate.now());
		Employee e2 = new Employee("Marisa Souza", "marisa@gmail.com", LocalDate.parse("1985-07-03"), LocalDate.now());
		Employee e3 = new Employee("Bruno Cintra", "bruno@gmail.com", LocalDate.parse("1980-11-29"), LocalDate.now());

		employeeRepository.saveAll(Arrays.asList(e1, e2, e3));

		Address ae1 = new Address("Rua São Francisco", "291", "Apt 101", "Boa Vista", "Recife", State.PERNAMBUCO, e1);
		Address ae2 = new Address("Rua Presidente Vargas", "11", null, "Cidade Nova", "Ribeirão Preto", State.SAO_PAULO,
				e2);
		Address ae3 = new Address("Av Paulista", "1052", "Bloco A, Apto 11", "Centro", "São Paulo", State.SAO_PAULO,
				e3);

		e1.setAddress(ae1);
		e2.setAddress(ae2);
		e3.setAddress(ae3);

		employeeRepository.saveAll(Arrays.asList(e1, e2, e3));

		s1.getEmployees().add(e1);
		s1.getEmployees().add(e2);
		s2.getEmployees().add(e2);
		s2.getEmployees().add(e3);

		squadRepository.saveAll(Arrays.asList(s1, s2));

		Vacation v1 = new Vacation(LocalDate.parse("2020-01-21"), LocalDate.parse("2020-02-20"), true);
		Vacation v2 = new Vacation(LocalDate.parse("2020-02-20"), LocalDate.parse("2020-03-20"), true);
		v1.setEmployee(e1);
		v2.setEmployee(e2);
		vacationRepository.saveAll(Arrays.asList(v1, v2));

	}
}
