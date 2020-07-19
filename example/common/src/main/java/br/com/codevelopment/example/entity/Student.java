package br.com.codevelopment.example.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private String lastName;
	
	@Column
	private String cpf;
	
	@Column
	private String rg;
	
	@Column
	private LocalDate birthDate;
	
	@Column
	private Integer semester;
	
	@Column
	private Integer year;
	
}
