package br.com.codevelopment.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codevelopment.example.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
