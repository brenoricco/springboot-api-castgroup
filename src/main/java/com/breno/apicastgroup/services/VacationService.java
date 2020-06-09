package com.breno.apicastgroup.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breno.apicastgroup.entities.Employee;
import com.breno.apicastgroup.entities.Squad;
import com.breno.apicastgroup.entities.Vacation;
import com.breno.apicastgroup.repositories.EmployeeRepository;
import com.breno.apicastgroup.repositories.VacationRepository;
import com.breno.apicastgroup.services.exception.InvalidInputDataException;
import com.breno.apicastgroup.services.exception.ObjectNotFoundException;
import com.breno.apicastgroup.services.exception.OrderDeclinedException;

@Service
public class VacationService {

	@Autowired
	VacationRepository repo;
	
	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	EmployeeService serviceEmployee;

	public List<Vacation> findAll() {
		return repo.findAll();
	}
	
	public Set<Employee> findEmployeesMustRequestVacation(Integer monthsNumber) {
		Set<Employee> list = new HashSet<>();
		
		for (Employee employee : serviceEmployee.findAll()) {
			if(employee.getHiringDate().until(LocalDate.now(), ChronoUnit.YEARS) >= 2 &&
			   (employee.getVacation() == null || employee.getVacation().getEndDate().plusMonths(monthsNumber).until(LocalDate.now(), ChronoUnit.YEARS) >= 2)) {
				list.add(employee);
			}
		}				
		
		return list;
	}

	public Vacation findById(Long id) {				
		try {
			Optional<Vacation> obj = repo.findById(id);
			if(obj.get() == null) {
				throw new ObjectNotFoundException("Férias não encontrada");
			}
			return obj.get();
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("Férias não encontrada");
		} 
	}

	public Vacation findByEmployeeId(Long id) {
		try {
			Employee obj = serviceEmployee.findById(id);
			if (obj.getVacation() == null) {
				throw new ObjectNotFoundException("Esse funcionário não possui nenhum registro de férias");
			}
			return obj.getVacation();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
	}
	
	public Vacation findByEmployeeRegistration(String registration) {
		try {
			Vacation result = findAll().stream().filter(v -> v.getEmployee().getRegistration().equals(registration)).findAny().orElse(null);
			if (result == null) {
				throw new ObjectNotFoundException("Nenhum registro de férias encontrado para essa matricula");
			}
			return result;
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Registro não encontrado");
		}
	}

	public Vacation insert(Long id, Vacation obj) {
		if (obj == null) {
			throw new InvalidInputDataException("Dados de entrada invalidos.");
		}
		Employee employee = serviceEmployee.findById(id);
		
		long yearDiff = employee.getHiringDate().until(LocalDate.now(), ChronoUnit.YEARS);
		if(yearDiff < 1) {
			throw new OrderDeclinedException("Recusado, o funcionário possui menos de um ano de contrato.");
		}
		
		if(!checkIfEmployeeCanTakeVacation(employee)) {
			throw new OrderDeclinedException("Periodo não permitido, outros funcionários da equipe ja reservaram esse periodo");
		}
		
		Vacation vacation = new Vacation(obj.getStartDate(), obj.getEndDate(), obj.getEnjoyed());
		vacation.setEmployee(employee);
		return repo.save(vacation);		
	}

	public Vacation updateByEmployeeId(Long id, Vacation obj) {
		if (obj == null) {
			throw new InvalidInputDataException("Dados insira os dados corretamente para alteração.");
		}
		Vacation entity = findByEmployeeId(id);
		entity.setStartDate(obj.getStartDate());
		entity.setEndDate(obj.getEndDate());
		entity.setEnjoyed(obj.getEnjoyed());
		
		return repo.save(entity);
	}
	
	public void delete(Long id) {
		if(findById(id) != null) {
			repo.deleteById(id);
		}
	}
	
	private boolean checkIfEmployeeCanTakeVacation(Employee employee) {
		
		Set<Squad> squadsMax4Peaple = employee.getSquads().stream().filter(s -> s.getEmployees().size() <= 4).collect(Collectors.toSet());
		for (Squad squad : squadsMax4Peaple) {
			for (Employee emp : squad.getEmployees()) {
				Period period = Period.between(emp.getVacation().getStartDate(), emp.getVacation().getEndDate());
				if(period == Period.between(employee.getVacation().getStartDate(), employee.getVacation().getEndDate())) {
					return false;
				}
			}
		}
		
		return true;
	}
	
}
