package br.com.codevelopment.example.batch.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codevelopment.example.service.contract.PersonService;

@RestController
@RequestMapping("/api/v1")
public class PersonController {
	private PersonService service;

	public PersonController(PersonService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create/person/{number}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createPersons(@PathVariable int number) {
		service.generatePerson(number);
	}
}
