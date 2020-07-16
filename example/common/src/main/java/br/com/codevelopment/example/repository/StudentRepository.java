package br.com.codevelopment.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.codevelopment.example.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
