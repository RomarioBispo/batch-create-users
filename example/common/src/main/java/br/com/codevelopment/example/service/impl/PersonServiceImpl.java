package br.com.codevelopment.example.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import br.com.codevelopment.example.entity.Person;
import br.com.codevelopment.example.repository.PersonRepository;
import br.com.codevelopment.example.service.contract.PersonService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService{
	
	private PersonRepository repository;
	
	public PersonServiceImpl(PersonRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void generatePerson(int number) {
		log.debug("Creating {} persons", number);
		for (int i = 0; i < number; i++) {
			Person p = new Person();
			p.setName("Person");
			p.setLastName("Person LastName");
			p.setRg("5555555-5");
			p.setCpf("555555555-55");
			p.setBirthDate(LocalDate.now());
			repository.save(p);
		}
	}
	
}
