package br.com.codevelopment.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.codevelopment.example.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
