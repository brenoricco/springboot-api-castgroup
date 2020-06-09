package com.breno.apicastgroup.resources;

import java.net.URI;
import java.util.List;

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

import com.breno.apicastgroup.dto.SquadDTO;
import com.breno.apicastgroup.entities.Squad;
import com.breno.apicastgroup.services.SquadService;

@RestController
@RequestMapping(value = "/squads")
public class SquadResource {

	@Autowired
	SquadService service;

	@GetMapping
	public ResponseEntity<List<SquadDTO>> findAll() {
		List<Squad> list = service.findAll();
		
		return ResponseEntity.ok().body(service.toListDTO(list));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SquadDTO> findById(@PathVariable Long id) {
		Squad obj = service.findById(id);
		
		return ResponseEntity.ok().body(service.toDTO(obj));
	}

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Squad obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Squad> update(@RequestBody Squad obj, @PathVariable Long id) {
		obj = service.update(id, obj);
		return ResponseEntity.noContent().build();
	}
}
