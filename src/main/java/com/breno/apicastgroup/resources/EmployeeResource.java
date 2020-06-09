package com.breno.apicastgroup.resources;

import java.net.URI;
import java.nio.file.Path;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.breno.apicastgroup.entities.Employee;
import com.breno.apicastgroup.services.EmployeeService;
import com.breno.apicastgroup.services.MailerService;
import com.breno.apicastgroup.services.util.QRCodeGenerator;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeResource {
	
	@Autowired
	EmployeeService service;
	
	@Autowired
	MailerService mailer;
	
	@GetMapping
	public ResponseEntity<List<Employee>> findAll() {
		List<Employee> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Employee> findById(@PathVariable Long id) {
		Employee obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Employee obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
		String pathQrCode = QRCodeGenerator.generateEmployeeQRCode(obj);
		mailer.sendMailWithFile(obj, pathQrCode);
		
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Employee> update(@RequestBody Employee obj, @PathVariable Long id) {
		obj = service.update(id, obj);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/{id}/uploadFile")
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file, @PathVariable Long id) {
		Path path = service.saveImage(file, id);
		return ResponseEntity.created(path.toUri()).build();
	}
}
