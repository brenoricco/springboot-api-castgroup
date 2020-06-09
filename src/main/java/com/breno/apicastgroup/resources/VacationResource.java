package com.breno.apicastgroup.resources;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.breno.apicastgroup.entities.Employee;
import com.breno.apicastgroup.entities.Vacation;
import com.breno.apicastgroup.services.VacationService;

@RestController
@RequestMapping(value = "/vacations")
public class VacationResource {

	@Autowired
	VacationService service;
	
	@GetMapping
	public ResponseEntity<List<Vacation>> findAll() {
		List<Vacation> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Vacation> findById(@PathVariable Long id) {
		Vacation obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping(value = "/employees/{registration}")
	public ResponseEntity<Vacation> findByEmployeeRegistration(@PathVariable String registration) {
		Vacation obj = service.findByEmployeeRegistration(registration);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping(value = "/employees/{id}")
	public ResponseEntity<Void> insert(@RequestBody Vacation obj, @PathVariable Long id) {
		obj = service.insert(id, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/employees/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/employees/{id}")
	public ResponseEntity<Vacation> update(@RequestBody Vacation obj, @PathVariable Long id) {
		obj = service.updateByEmployeeId(id, obj);
		return ResponseEntity.noContent().build();
	}	
	
	@GetMapping(value = "/employees/needvacation/{monthsNumber}")
	public ResponseEntity<Set<Employee>> findEmployeesMustRequestVacation(@PathVariable Integer monthsNumber) {
		Set<Employee> list = service.findEmployeesMustRequestVacation(monthsNumber);
		return ResponseEntity.ok().body(list);
	}

}
